package com.luckwheat.notes.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@MicronautTest
@Testcontainers
class IndexingServiceTest {

    @Inject
    IndexingService service;

    @Test
    void testIndexing() {
        // WHEN
        Executable command = () -> service.initiateIndexing();

        // THEN
        assertDoesNotThrow(command);
    }


}