package com.luckwheat.notes.service;

import com.luckwheat.notes.dto.NoteDto;
import com.luckwheat.notes.dto.ProjectDto;
import com.luckwheat.notes.dto.auth0.UserInfo;
import com.luckwheat.notes.entity.User;
import com.luckwheat.notes.repository.UserRepository;
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
        assertTrue(result.isSuccess());
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
