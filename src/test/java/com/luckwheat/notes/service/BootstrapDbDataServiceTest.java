package com.luckwheat.notes.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@Testcontainers
class BootstrapDbDataServiceTest {

    @Inject
    ProjectService projectService;

    @Test
    void testCreateDefaultProject() {
        var bootstrappedProject = projectService.findByName("Personal");

        assertTrue(bootstrappedProject.isPresent());
    }

}