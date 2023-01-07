package com.panfutov.notes.dto;

import java.time.LocalDateTime;

public record NoteDto(
        Long id,
        String name,
        String content,

        String contentPreview,
        LocalDateTime createdTimestamp,
        LocalDateTime updatedTimestamp,
        Long projectId) {

    public static NoteDto newNote(String name, String content, Long projectId) {
        return new NoteDto(null,name, content, null, null, null, projectId);
    }

}
