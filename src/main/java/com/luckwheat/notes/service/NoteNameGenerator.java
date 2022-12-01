package com.luckwheat.notes.service;

import com.luckwheat.notes.repository.NoteRepository;
import com.luckwheat.notes.service.exception.NoteNameGenerationException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
public class NoteNameGenerator {

    private final NoteRepository noteRepository;

    @Inject
    public NoteNameGenerator(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public String generateNoteName() {
        String baseName = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + " New Note";

        if (!noteRepository.existsByName(baseName)) {
            return baseName;
        }

        return appendNumber(baseName);
    }

    private String appendNumber(String baseName) {
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String candidate = MessageFormat.format("{0} ({1})", baseName, i);

            if (!noteRepository.existsByName(candidate)) {
                return candidate;
            }
        }

        throw new NoteNameGenerationException("Could not find a free name");
    }
}
