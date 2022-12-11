package com.luckwheat.notes.repository;

import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.entity.User;
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

    @Inject
    UserRepository userRepository;

    @Test
    void testSaveProject() {
        final var newProject = new Project();
        newProject.setName("Super project");
        newProject.setUser(createUser());

        final var saved = projectRepository.save(newProject);

        assertEquals(newProject.getName(), saved.getName());
    }

    private User createUser() {
        var user = new User();
        user.setSub("sub");
        return userRepository.save(user);
    }
}
