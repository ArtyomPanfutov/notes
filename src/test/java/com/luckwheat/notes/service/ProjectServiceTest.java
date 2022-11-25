package com.luckwheat.notes.service;

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
        // GIVEN
        final var newProject = new ProjectDto(null, "name");

        // WHEN
        final var result = projectService.create(newProject);

        // THEN
        assertTrue(result.isSuccess());
        assertNotNull(result.body().id());
        assertEquals(newProject.name(), result.body().name());
    }

    @Test
    void testCreateWithNullName() {
        // WHEN
        final var result = projectService.create(new ProjectDto(null, null));

        // THEN
        assertFalse(result.isSuccess());
        assertNotNull(result.error());
    }

    @Test
    void testCreateWithNullInput() {
        // WHEN
        final var result = projectService.create(null);

        // THEN
        assertFalse(result.isSuccess());
        assertNotNull(result.error());
    }

    @Test
    void testFindAll() {
        // GIVEN
        projectService.create(new ProjectDto(null, "some project"));

        // WHEN
        final var result = projectService.findAll();

        // THEN
        assertFalse(result.isEmpty());
    }
}
