package com.panfutov.notes.service;

import com.panfutov.notes.dto.NoteDto;
import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.NoteRepository;
import com.panfutov.notes.repository.UserRepository;
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

    @Inject
    private UserRepository userRepository;

    @Test
    void testGenerateName() {
        // GIVEN
        var user = createUser();
        long projectId = projectService.getDefaultProject(user).id();

        // WHEN
        List<String> names = IntStream.rangeClosed(0, 5)
                .mapToObj(generateAndSave(projectId, user))
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
    private IntFunction<String> generateAndSave(long projectId, UserInfo userInfo) {
        return (num -> {
            var name = noteNameGenerator.generateNoteName(userInfo);
            // Save a note to reserve the generated name
            noteService.create(NoteDto.newNote(name, "content", projectId), userInfo);
            return name;
        });
    }

    private UserInfo createUser() {
        var user = new User();
        user.setSub("auth0sub");
        userRepository.save(user);
        return new UserInfo(user.getSub(), "email");
    }

}