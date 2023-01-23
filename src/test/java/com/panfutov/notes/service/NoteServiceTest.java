package com.panfutov.notes.service;

import com.panfutov.notes.dto.NoteDto;
import com.panfutov.notes.dto.ProjectDto;
import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.UserRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@Testcontainers
class NoteServiceTest {

    @Inject
    NoteService noteService;

    @Inject
    ProjectService projectService;

    @Inject
    UserRepository userRepository;

    @Test
    void testCreateAndUpdateNote() {
        NoteDto created = testCreate();
        testUpdate(created);
    }

    private void testUpdate(NoteDto created) {
        // GIVEN
        var forUpdate = new NoteDto(
                created.id(),
                "Name updated",
                "Content updated",
                null,
                null,
                null,
                created.projectId());

        // WHEN
        var updated = noteService.update(forUpdate);

        // THEN
        assertTrue(updated.isSuccess());
        assertNotNull(updated.body().id());
        var selected = noteService.findById(updated.body().id());
        assertTrue(selected.isPresent());
        assertEquals(created.createdTimestamp(), selected.get().createdTimestamp());
        assertTrue(created.updatedTimestamp().isBefore(selected.get().updatedTimestamp()));
    }

    @NotNull
    private NoteDto testCreate() {
        // GIVEN
        var user = createUser();
        final var project = projectService.create(ProjectDto.newProject("Unknown"), user);

        assertTrue(project.isSuccess());

        // WHEN
        final var result = noteService.create(
                NoteDto.newNote("test", "some content", project.body().id()), user);

        // THEN
        Assertions.assertTrue(result.isSuccess());
        assertNotNull(result.body().id());
        var selected = noteService.findById(result.body().id());
        assertTrue(selected.isPresent());
        assertNotNull(selected.get().createdTimestamp());
        assertNotNull(selected.get().updatedTimestamp());
        return selected.get();
    }

    private UserInfo createUser() {
        var user = new User();
        user.setSub("sub");
        userRepository.save(user);

        return new UserInfo(user.getSub(), "email");
    }
}
