package com.panfutov.notes.controller;

import com.panfutov.notes.dto.ProjectDto;
import com.panfutov.notes.dto.ResultPage;
import com.panfutov.notes.service.ProjectService;
import com.panfutov.notes.service.UserService;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/api/projects")
@Secured(IS_AUTHENTICATED)
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    private final UserService userService;

    @Inject
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<Void> create(@Body ProjectDto projectDto, @Header("Authorization") String authorization) {
        var user = userService.getUserByAuthorizationHeader(authorization);
        final var result = projectService.create(projectDto, user);

        if (result.isSuccess()) {
            return HttpResponse.ok();
        }

        log.error("Error creating a project: {}", result.error());
        // TODO: Error handling
        return HttpResponse.badRequest();
    }

    @Put
    public HttpResponse<Void> update(@Body ProjectDto projectDto) {
        final var result = projectService.update(projectDto);

        if (result.isError()) {
            return HttpResponse.badRequest();
        }

        return HttpResponse.ok();
    }

    @Get("/pageable")
    public HttpResponse<ResultPage<ProjectDto>> findAll(@Header("Authorization") String authorization,
                                                        @QueryValue int page,
                                                        @QueryValue int pageSize) {
        var user = userService.getUserByAuthorizationHeader(authorization);
        return HttpResponse.ok(projectService.findAllByUser(user, Pageable.from(page, pageSize)));
    }

    @Get
    public HttpResponse<List<ProjectDto>> findAll(@Header("Authorization") String authorization) {
        var user = userService.getUserByAuthorizationHeader(authorization);
        return HttpResponse.ok(projectService.findAllByUser(user));
    }

    @Get("/{id}")
    public HttpResponse<ProjectDto> findById(@QueryValue Long id) {
        final var project = projectService.findById(id);

        if (project.isEmpty()) {
            return HttpResponse.notFound();
        }

        return HttpResponse.ok(project.get());
    }

    @Get("/name/{name}")
    public HttpResponse<ProjectDto> findByName(@QueryValue String name, @Header("Authorization") String authorization) {
        var user = userService.getUserByAuthorizationHeader(authorization);
        final var project = projectService.findByNameAndUserInfo(name, user);

        if (project.isEmpty()) {
            return HttpResponse.notFound();
        }

        return HttpResponse.ok(project.get());
    }

    @Get("/default")
    public HttpResponse<ProjectDto> getDefaultProject(@Header("Authorization") String authorization) {
        var user = userService.getUserByAuthorizationHeader(authorization);
        return HttpResponse.ok(projectService.getDefaultProject(user));
    }

    @Delete("/{id}")
    public HttpResponse<String> delete(@QueryValue Long id) {
        final var result = projectService.delete(id);

        if (result.isError()) {
            return HttpResponse.badRequest(result.error().message());
        }

        return HttpResponse.ok();
    }
}
