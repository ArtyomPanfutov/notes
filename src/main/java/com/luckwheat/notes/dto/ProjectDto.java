package com.luckwheat.notes.dto;

public record ProjectDto(Long id, String name) {

    public static ProjectDto newProject(String name) {
        return new ProjectDto(null, name);
    }
}
