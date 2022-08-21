package com.luckwheat.notes.controller;

import com.luckwheat.notes.dto.ProjectDto;
import com.luckwheat.notes.service.ProjectService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller("/api/projects")
@Slf4j
public class ProjectController {

    @Inject
    ProjectService projectService;

    @Post(consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<Void> create(@Body ProjectDto projectDto) {
        final var result = projectService.create(projectDto);

        if (result.success()) {
            return HttpResponse.ok();
        }

        log.error("Error creating a project: {}", result.error());
        // TODO: Error handling
        return HttpResponse.badRequest();
    }

    @Get
    public HttpResponse<List<ProjectDto>> findAll() {
        return HttpResponse.ok(projectService.findAll());
    }

    @Delete("/{id}")
    public HttpResponse<String> delete(@QueryValue Long id) {
        final var result = projectService.delete(id);

        if (result.fail()) {
            return HttpResponse.badRequest(result.error().message());
        }

        return HttpResponse.ok();
    }
}
