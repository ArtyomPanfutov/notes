package com.luckwheat.notes.repository;

import com.luckwheat.notes.entity.Note;
import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.repository.NoteRepository;
import com.luckwheat.notes.repository.ProjectRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
@Testcontainers
class NoteRepositoryTest {

    @Inject
    NoteRepository noteRepository;

    @Inject
    ProjectRepository projectRepository;

    @Test
    void testCreateNote() {
        final var note = new Note();
        note.setName("new note");
        note.setContent("content");
        note.setCreatedTimestamp(LocalDateTime.now());
        note.setUpdatedTimestamp(LocalDateTime.now());
        note.setProject(createProject());

        final var saved = noteRepository.save(note);

        assertEquals(note.getName(), saved.getName());
    }

    private Project createProject() {
        final var project = new Project();
        project.setName("project");
        return projectRepository.save(project);
    }
}
