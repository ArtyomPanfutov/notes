package com.luckwheat.notes.controller;

import com.luckwheat.notes.dto.ProjectDto;
import com.luckwheat.notes.service.ProjectService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/api/project")
public class ProjectController {

    @Inject
    ProjectService projectService;

    @Post(consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<Void> create(@Body ProjectDto projectDto) {
        throw new UnsupportedOperationException("not implemented");
    }
}
