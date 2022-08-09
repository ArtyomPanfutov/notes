package com.luckwheat.notes.dto;

import java.time.LocalDateTime;

public record NoteDto(
        Long id,
        String name,
        String content,
        LocalDateTime createdTimestamp,
        LocalDateTime updatedTimestamp,
        Long projectId) {

    public static NoteDto newNote(String name, String content, Long projectId) {
        return new NoteDto(null,name, content, null, null, projectId);
    }

}
