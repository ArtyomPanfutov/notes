package com.panfutov.notes.repository;

import com.panfutov.notes.entity.Note;
import com.panfutov.notes.entity.Project;
import com.panfutov.notes.entity.User;
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

    @Inject
    UserRepository userRepository;

    @Test
    void testCreateNote() {
        final var note = new Note();
        note.setName("new note");
        note.setContent("content");
        note.setPreview("preview");
        note.setUpdatedTimestamp(LocalDateTime.now());
        note.setProject(createProject());
        note.setUser(createUser());

        final var saved = noteRepository.save(note);

        assertEquals(note.getName(), saved.getName());
    }

    private Project createProject() {
        final var project = new Project();
        project.setName("project");
        return projectRepository.save(project);
    }

    private User createUser() {
        var user = new User();
        user.setSub("sub");
        return userRepository.save(user);
    }
}
