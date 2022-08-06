package com.luckwheat.service;

import com.luckwheat.notes.dto.ProjectDto;
import com.luckwheat.notes.repository.ProjectRepository;
import com.luckwheat.notes.service.ProjectService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@Testcontainers
class ProjectServiceTest {

    @Inject
    ProjectService projectService;

    @Inject
    ProjectRepository projectRepository;

    @Test
    void testCreateProject() {
        final var newProject = new ProjectDto(null, "name");

        final var result = projectService.create(newProject);

        assertTrue(result.success());
        assertNotNull(result.body().id());
        assertEquals(newProject.name(), result.body().name());
    }

    @Test
    void testCreateWithNullName() {
        final var result = projectService.create(new ProjectDto(null, null));

        assertFalse(result.success());
        assertNotNull(result.error());
        assertEquals(0, projectRepository.count());
    }

    @Test
    void testCreateWithNullInput() {
        final var result = projectService.create(null);

        assertFalse(result.success());
        assertNotNull(result.error());
        assertEquals(0, projectRepository.count());
    }

    @Test
    void testFindAll() {
        projectService.create(new ProjectDto(null, "some project"));
        final var result = projectService.findAll();
        assertFalse(result.isEmpty());
    }
}
