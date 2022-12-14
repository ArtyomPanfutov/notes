package com.luckwheat.notes.service;

import com.google.common.collect.ImmutableList;
import com.luckwheat.notes.dto.Error;
import com.luckwheat.notes.dto.ProjectDto;
import com.luckwheat.notes.dto.Result;
import com.luckwheat.notes.dto.ResultPage;
import com.luckwheat.notes.dto.auth0.UserInfo;
import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.entity.User;
import com.luckwheat.notes.repository.ProjectRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProjectService {

    public static final String DEFAULT_PROJECT_NAME = "Personal";

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private UserService userService;

    @Transactional
    public Result<ProjectDto> create(ProjectDto projectDto, UserInfo userInfo) {
        if (projectDto == null) {
            return Result.error(new Error("Can't create the project that is null"));
        }

        if (projectDto.name() == null || projectDto.name().isBlank()) {
            return Result.error(new Error("The name of the project can't be empty"));
        }

        if (projectDto.id() != null) {
            return Result.error(new Error("The id must be null before creation. It will be assigned automatically."));
        }

        var user = userService.getUserByUserInfo(userInfo);

        final var saved = projectRepository.save(convertToEntity(projectDto, user));

        return Result.success(convertToDto(saved));
    }

    @Transactional
    public Result<ProjectDto> update(ProjectDto projectDto) {
        if (projectDto == null) {
            return Result.error(new Error("Can't update the project that is null"));
        }

        final var project = projectRepository.findById(projectDto.id());
        if (project.isPresent()) {
            final var forUpdate = project.get();

            forUpdate.setName(projectDto.name());

            return Result.success(convertToDto(projectRepository.save(forUpdate)));
        }

        return Result.error(new Error("The project is not found"));
    }

    @Transactional
    public ResultPage<ProjectDto> findAllByUser(UserInfo userInfo, Pageable pageable) {
        var user = userService.getUserByUserInfo(userInfo);
        final var projects  = projectRepository.findAllByUser(user, pageable);
        final var result = ImmutableList.<ProjectDto>builder();

        for (Project project : projects) {
            result.add(convertToDto(project));
        }

        return new ResultPage<>(result.build(), projects.getPageNumber(), projects.getTotalPages());
    }

    @Transactional
    public Optional<ProjectDto> findById(Long id) {
        return projectRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional
    public Optional<ProjectDto> findByNameAndUserInfo(String name, UserInfo userInfo) {
        var user = userService.getUserByUserInfo(userInfo);
        return projectRepository.findByNameAndUser(name, user)
                .map(this::convertToDto);
    }

    @Transactional
    public Result<Void> delete(Long id) {
        final var found = projectRepository.findById(id);

        if (found.isEmpty()) {
            return Result.error(new Error("Project doesn't exists"));
        }

        projectRepository.deleteById(id);

        return Result.successVoid();
    }

    @Transactional
    public ProjectDto getDefaultProject(UserInfo userInfo) {
        var user = userService.getUserByUserInfo(userInfo);
        if (projectRepository.findByNameAndUser(DEFAULT_PROJECT_NAME, user).isEmpty()) {

            var project = new Project();
            project.setName(DEFAULT_PROJECT_NAME);
            project.setUser(user);

            projectRepository.save(project);
        }

        return projectRepository.findByNameAndUser(DEFAULT_PROJECT_NAME, user)
                .map(this::convertToDto)
                .orElseThrow();
    }

    private ProjectDto convertToDto(Project project) {
        return new ProjectDto(project.getId(), project.getName());
    }

    private Project convertToEntity(ProjectDto projectDto, User user) {
        final var project = new Project();

        project.setId(projectDto.id());
        project.setName(projectDto.name());
        project.setUser(user);

        return project;
    }
}
