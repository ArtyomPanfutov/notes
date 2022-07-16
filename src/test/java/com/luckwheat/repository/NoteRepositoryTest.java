package com.luckwheat.repository;

import com.luckwheat.notes.entity.Note;
import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.repository.NoteRepository;
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

    @Test
    void testCreateNote() {
        final var note = new Note();
        note.setName("new note");
        note.setContent("content");
        note.setCreatedTimestamp(LocalDateTime.now());
        note.setUpdatedTimestamp(LocalDateTime.now());
        note.setProject(new Project());

        final var saved = noteRepository.save(note);

        assertEquals(note.getName(), saved.getName());
    }
}
