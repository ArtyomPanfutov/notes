package com.luckwheat.service;

import com.luckwheat.notes.dto.NoteDto;
import com.luckwheat.notes.dto.ProjectDto;
import com.luckwheat.notes.service.NoteService;
import com.luckwheat.notes.service.ProjectService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
@Testcontainers
class NoteServiceTest {

    @Inject
    NoteService noteService;

    @Inject
    ProjectService projectService;

    @Test
    void testCreateNote() {
        final var project = projectService.create(ProjectDto.newProject("Unknown"));

        assertTrue(project.success());

        final var result = noteService.create(NoteDto.newNote("test", "some content", project.body().id()));

        assertTrue(result.success());
        assertNotNull(result.body().id());
        assertNotNull(result.body().createdTimestamp());
        assertNotNull(result.body().updatedTimestamp());
    }
}
