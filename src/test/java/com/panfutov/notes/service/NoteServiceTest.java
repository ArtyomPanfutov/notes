package com.panfutov.notes.service;

import com.panfutov.notes.dto.NoteDto;
import com.panfutov.notes.dto.ProjectDto;
import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.UserRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
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

    @Inject
    UserRepository userRepository;

    @Test
    void testCreateNote() {
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
        assertNotNull(result.body().createdTimestamp());
        assertNotNull(result.body().updatedTimestamp());
    }

    private UserInfo createUser() {
        var user = new User();
        user.setSub("sub");
        userRepository.save(user);

        return new UserInfo(user.getSub(), "email");
    }
}
