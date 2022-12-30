package com.panfutov.notes.service;

import com.panfutov.notes.dto.ProjectDto;
import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.ProjectRepository;
import com.panfutov.notes.repository.UserRepository;
import io.micronaut.data.model.Pageable;
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

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Test
    void testCreateProject() {
        // GIVEN
        var user = createUser();
        final var newProject = new ProjectDto(null, "name");

        // WHEN
        final var result = projectService.create(newProject, user);

        // THEN
        assertTrue(result.isSuccess());
        assertNotNull(result.body().id());
        assertEquals(newProject.name(), result.body().name());
    }

    @Test
    void testCreateWithNullName() {
        // WHEN
        var userInfo = createUser();
        final var result = projectService.create(new ProjectDto(null, null), userInfo);

        // THEN
        assertFalse(result.isSuccess());
        assertNotNull(result.error());
    }

    @Test
    void testCreateWithNullInput() {
        // WHEN
        var user = createUser();
        final var result = projectService.create(null, user);

        // THEN
        assertFalse(result.isSuccess());
        assertNotNull(result.error());
    }

    @Test
    void testFindAll() {
        // GIVEN
        var user = createUser();
        projectService.create(new ProjectDto(null, "some project"), user);

        // WHEN
        final var result = projectService.findAllByUser(user, Pageable.from(0, 10));

        // THEN
        assertFalse(result.items().isEmpty());
    }

    public UserInfo createUser() {
        var user = new User();
        user.setSub("auth0sub");
        userRepository.save(user);

        return new UserInfo(user.getSub(), "");
    }
}
