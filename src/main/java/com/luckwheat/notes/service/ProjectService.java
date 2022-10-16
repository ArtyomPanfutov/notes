package com.luckwheat.notes.service;

import com.google.common.collect.ImmutableList;
import com.luckwheat.notes.dto.Error;
import com.luckwheat.notes.dto.ProjectDto;
import com.luckwheat.notes.dto.Result;
import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.repository.ProjectRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProjectService {

    @Inject
    private ProjectRepository projectRepository;

    @Transactional
    public Result<ProjectDto> create(ProjectDto projectDto) {
        if (projectDto == null) {
            return Result.error(new Error("Can't create the project that is null"));
        }

        if (StringUtils.isEmpty(projectDto.name())) {
            return Result.error(new Error("The name of the project can't be empty"));
        }

        if (projectDto.id() != null) {
            return Result.error(new Error("The id must be null before creation. It will be assigned automatically."));
        }

        final var saved = projectRepository.save(convertToEntity(projectDto));

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
    public List<ProjectDto> findAll() {
        final var projects  = projectRepository.findAll();
        final var result = ImmutableList.<ProjectDto>builder();

        for (Project project : projects) {
            result.add(convertToDto(project));
        }

        return result.build();
    }

    @Transactional
    public Optional<ProjectDto> findById(Long id) {
        return projectRepository.findById(id)
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

    private ProjectDto convertToDto(Project project) {
        return new ProjectDto(project.getId(), project.getName());
    }

    private Project convertToEntity(ProjectDto projectDto) {
        final var project = new Project();

        project.setId(projectDto.id());
        project.setName(projectDto.name());

        return project;
    }
}
