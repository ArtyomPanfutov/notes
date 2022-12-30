package com.panfutov.notes.service;

import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.NoteRepository;
import com.panfutov.notes.service.exception.NoteNameGenerationException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
public class NoteNameGenerator {

    private final NoteRepository noteRepository;

    private final UserService userService;

    @Inject
    public NoteNameGenerator(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public String generateNoteName(UserInfo userInfo) {
        String baseName = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + " New Note";

        var user = userService.getUserByUserInfo(userInfo);
        if (!noteRepository.existsByNameAndUser(baseName, user)) {
            return baseName;
        }

        return appendNumber(baseName, user);
    }

    private String appendNumber(String baseName, User user) {
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String candidate = MessageFormat.format("{0} ({1})", baseName, i);

            if (!noteRepository.existsByNameAndUser(candidate, user)) {
                return candidate;
            }
        }

        throw new NoteNameGenerationException("Could not find a free full name for base name - " + baseName);
    }
}
