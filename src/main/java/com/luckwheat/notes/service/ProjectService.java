package com.luckwheat.notes.service;

import com.luckwheat.notes.dto.CreateResult;
import com.luckwheat.notes.dto.ProjectDto;
import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.repository.ProjectRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;

@Singleton
public class ProjectService {

    @Inject
    private ProjectRepository projectRepository;

    @Transactional
    public CreateResult<ProjectDto> create(ProjectDto projectDto) {
        final var saved = projectRepository.save(convertToEntity(projectDto));

        return CreateResult.success(convertToDto(saved));
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
