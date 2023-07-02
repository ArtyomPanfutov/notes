package com.panfutov.notes.service;

import com.google.common.collect.ImmutableList;
import com.panfutov.notes.dto.*;
import com.panfutov.notes.dto.Error;
import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.DailyPlan;
import com.panfutov.notes.entity.DailyTask;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.DailyPlanRepository;
import com.panfutov.notes.repository.DailyTaskRepository;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class DailyPlanService {

    private final DailyPlanRepository dailyPlanRepository;

    private final DailyTaskRepository dailyTaskRepository;

    private final UserService userService;

    @Inject
    public DailyPlanService(DailyPlanRepository dailyPlanRepository,
                            DailyTaskRepository dailyTaskRepository,
                            UserService userService) {
        this.dailyPlanRepository = dailyPlanRepository;
        this.dailyTaskRepository = dailyTaskRepository;
        this.userService = userService;
    }

    @Transactional
    public Result<DailyPlanDto> create(DailyPlanDto dto, UserInfo userInfo) {
        if (dto == null) {
            return Result.error(new Error("Can't create a plan that is null"));
        }

        // TODO: Validate tasks — do not allow to change existing tasks.
        //  They should be allowed to reuse, but not to update.
        var user = userService.getUserByUserInfo(userInfo);

        var saved = dailyPlanRepository.save(convertToEntity(dto, user));

        return Result.success(convertToDto(saved));

    }

    @Transactional
    public Result<DailyPlanDto> update(DailyPlanDto dto, UserInfo userInfo) {
        if (dto == null) {
            return Result.error(new Error("Can't update a null daily plan"));
        }

        var user = userService.getUserByUserInfo(userInfo);

        var entity = dailyPlanRepository.findById(dto.id());
        if (entity.isEmpty()) {
            return Result.error(new Error("The plan is not found"));
        }

        DailyPlan plan = entity.get();

        if (!user.equals(plan.getUser())) {
            return Result.error(new Error("The attempt to update a daily plan of a different user!"));
        }
        plan.setName(dto.name());
        plan.setTasks(convertTasksToEntity(dto.tasks(), user));
        plan.setUpdatedTimestamp(LocalDateTime.now());

        DailyPlan updated = dailyPlanRepository.save(plan);

        return Result.success(convertToDto(updated));
    }

    @Transactional
    public ResultPage<DailyPlanDto> findAllByUser(UserInfo userInfo, Pageable pageable) {
        var user = userService.getUserByUserInfo(userInfo);
        final var plans = dailyPlanRepository.findAllByUser(user, pageable);
        final var result = ImmutableList.<DailyPlanDto>builder();

        plans.forEach(plan -> result.add(convertToDto(plan)));

        return new ResultPage<>(result.build(), plans.getPageNumber(), plans.getTotalPages());
    }

    @Transactional
    public Optional<DailyPlanDto> findById(Long id) {
        return dailyPlanRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional
    public Result<Void> delete(Long id) {
        final var dailyPlan = dailyPlanRepository.findById(id);
        if (dailyPlan.isEmpty()) {
            return Result.error(new Error("The daily plan doesn't exist"));
        }
        dailyPlanRepository.deleteById(id);

        return Result.successVoid();
    }

    private DailyPlanDto convertToDto(DailyPlan entity) {
        return new DailyPlanDto(
                entity.getId(),
                entity.getName(),
                entity.getTargetDate(),
                entity.getCreatedTimestamp(),
                entity.getUpdatedTimestamp(),
                entity.getTasks()
                        .stream()
                        .map(this::convertTaskToEntity)
                        .collect(Collectors.toList()));
    }

    private DailyTaskDto convertTaskToEntity(DailyTask dailyTask) {
        return new DailyTaskDto(dailyTask.getId(), dailyTask.getText());
    }

    private DailyPlan convertToEntity(DailyPlanDto dto, User user) {
        var entity = new DailyPlan();

        entity.setUser(user);
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setTargetDate(dto.targetDate());

        entity.setTasks(convertTasksToEntity(dto.tasks(), user));

        return entity;
    }

    private List<DailyTask> convertTasksToEntity(List<DailyTaskDto> tasks , User user) {
        return tasks.stream()
                .map(task -> convertTaskDtoToEntity(task, user))
                .collect(Collectors.toList());
    }

    private DailyTask convertTaskDtoToEntity(DailyTaskDto dailyTaskDto, User user) {
        var entity = new DailyTask();
        entity.setId(dailyTaskDto.id());
        entity.setText(dailyTaskDto.text());
        entity.setUser(user);
        return entity;
    }
}
