package com.luckwheat.notes.service;

import com.google.common.collect.ImmutableList;
import com.luckwheat.notes.dto.CreateResult;
import com.luckwheat.notes.dto.Error;
import com.luckwheat.notes.dto.ProjectDto;
import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.repository.ProjectRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ProjectService {

    @Inject
    private ProjectRepository projectRepository;

    @Transactional
    public CreateResult<ProjectDto> create(ProjectDto projectDto) {
        if (projectDto == null) {
            return CreateResult.error(new Error("Can't create the project that is null"));
        }

        if (StringUtils.isEmpty(projectDto.name())) {
            return CreateResult.error(new Error("The name of the project can't be empty"));
        }

        if (projectDto.id() != null) {
            return CreateResult.error(new Error("The id must be null before creation. It will be assigned automatically."));
        }

        final var saved = projectRepository.save(convertToEntity(projectDto));

        return CreateResult.success(convertToDto(saved));
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
