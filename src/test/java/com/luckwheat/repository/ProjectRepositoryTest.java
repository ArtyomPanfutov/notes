package com.luckwheat.repository;

import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.repository.ProjectRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
@Testcontainers
class ProjectRepositoryTest {
    @Inject
    ProjectRepository projectRepository;

    @Test
    void testSaveProject() {
        final var newProject = new Project(null, "Super secret project");

        final var saved = projectRepository.save(newProject);

        assertEquals(newProject.getName(), saved.getName());
    }

}
