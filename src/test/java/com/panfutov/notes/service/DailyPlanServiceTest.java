package com.panfutov.notes.service;

import com.panfutov.notes.dto.DailyPlanDto;
import com.panfutov.notes.dto.DailyTaskDto;
import com.panfutov.notes.dto.Result;
import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.UserRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@Testcontainers
class DailyPlanServiceTest {

    @Inject
    private DailyPlanService dailyPlanService;

    @Inject
    private UserRepository userRepository;

    @Test
    void testCreateDailyPlanWithTask() {
        // GIVEN
        var user = createUser();
        LocalDate date = LocalDate.now();
        DailyPlanDto dto = DailyPlanDto.newDailyPlan("name", date, List.of(new DailyTaskDto(null, "new task")));

        // WHEN
        Result<DailyPlanDto> result = dailyPlanService.create(dto, user);

        // THEN
        assertTrue(result.isSuccess());
        assertNotNull(result.body());
        assertEquals(dto.name(), result.body().name());
        assertEquals(dto.targetDate(), result.body().targetDate());
        assertFalse(result.body().tasks().isEmpty());

        DailyTaskDto taskCreated = result.body().tasks().get(0);
        DailyTaskDto taskPassed = dto.tasks().get(0);
        assertEquals(taskPassed.text(), taskCreated.text());

        var selected = dailyPlanService.findById(result.body().id());
        assertFalse(selected.isEmpty());
        assertNotNull(selected.get().createdTimestamp());
        assertNotNull(selected.get().updatedTimestamp());
    }

    public UserInfo createUser() {
        var user = new User();
        user.setSub("auth0sub");
        userRepository.save(user);

        return new UserInfo(user.getSub(), "");
    }
}
