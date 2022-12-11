package com.luckwheat.notes.service;

import com.luckwheat.notes.dto.ProjectDto;
import com.luckwheat.notes.dto.auth0.UserInfo;
import com.luckwheat.notes.entity.User;
import com.luckwheat.notes.repository.ProjectRepository;
import com.luckwheat.notes.repository.UserRepository;
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
        final var result = projectService.findAllByUser(user);

        // THEN
        assertFalse(result.isEmpty());
    }

    public UserInfo createUser() {
        var user = new User();
        user.setSub("auth0sub");
        userRepository.save(user);

        return new UserInfo(user.getSub(), "");
    }
}
