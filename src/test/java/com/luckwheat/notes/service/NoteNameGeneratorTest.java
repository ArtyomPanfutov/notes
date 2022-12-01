package com.luckwheat.notes.service;

import com.luckwheat.notes.dto.NoteDto;
import com.luckwheat.notes.repository.NoteRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
@Testcontainers
@Slf4j
class NoteNameGeneratorTest {

    @Inject
    private NoteRepository noteRepository;

    @Inject
    private NoteService noteService;

    @Inject
    private NoteNameGenerator noteNameGenerator;

    @Inject
    private ProjectService projectService;

    @Test
    void testGenerateName() {
        // GIVEN
        long projectId = projectService.getDefaultProject().id();

        // WHEN
        List<String> names = IntStream.rangeClosed(0, 5)
                .mapToObj(generateAndSave(projectId))
                .toList();

        // THEN
        names.forEach(name -> assertFalse(Strings.isNullOrEmpty(name)));

        var duplicates = names.stream()
                .filter(name -> Collections.frequency(names, name) > 1)
                .collect(Collectors.toSet());

        log.info("Duplicates: {}", duplicates);
        assertTrue(duplicates.isEmpty());
    }

    @NotNull
    private IntFunction<String> generateAndSave(long projectId) {
        return (num -> {
            var name = noteNameGenerator.generateNoteName();
            // Save a note to reserve the generated name
            noteService.create(NoteDto.newNote(name, "content", projectId));
            return name;
        });
    }

}