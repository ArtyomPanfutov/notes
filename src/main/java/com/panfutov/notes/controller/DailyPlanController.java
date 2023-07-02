package com.panfutov.notes.controller;

import com.panfutov.notes.dto.DailyPlanDto;
import com.panfutov.notes.dto.Result;
import com.panfutov.notes.dto.ResultPage;
import com.panfutov.notes.service.DailyPlanService;
import com.panfutov.notes.service.UserService;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/api/daily-plans")
@Secured(IS_AUTHENTICATED)
@Slf4j
public class DailyPlanController {

    private final DailyPlanService dailyPlanService;

    private final UserService userService;

    @Inject
    public DailyPlanController(DailyPlanService dailyPlanService, UserService userService) {
        this.dailyPlanService = dailyPlanService;
        this.userService = userService;
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<Void> create(@Body DailyPlanDto dailyPlan, @Header("Authorization") String authorization) {
        var user = userService.getUserByAuthorizationHeader(authorization);

        Result<DailyPlanDto> result = dailyPlanService.create(dailyPlan, user);

        if (result.isSuccess()) {
            return HttpResponse.ok();
        }
        // TODO: proper error handling
        return HttpResponse.badRequest();
    }

    @Put
    public HttpResponse<Void> update(@Body DailyPlanDto dailyPlan, @Header("Authorization") String authorization) {
        var user = userService.getUserByAuthorizationHeader(authorization);

        Result<DailyPlanDto> result = dailyPlanService.update(dailyPlan, user);
        if (result.isSuccess()) {
            return HttpResponse.ok();
        }
        // TODO: proper error handling
        return HttpResponse.badRequest();
    }

    @Get("/pageable")
    public HttpResponse<ResultPage<DailyPlanDto>> findAll(@Header("Authorization") String authorization,
                                                        @QueryValue int page,
                                                        @QueryValue int pageSize) {
        var user = userService.getUserByAuthorizationHeader(authorization);
        return HttpResponse.ok(dailyPlanService.findAllByUser(user, Pageable.from(page, pageSize)));
    }

    @Get("/{id}")
    public HttpResponse<DailyPlanDto> findById(@QueryValue Long id) {
        final var project = dailyPlanService.findById(id);
        if (project.isEmpty()) {
            return HttpResponse.notFound();
        }

        return HttpResponse.ok(project.get());
    }

    @Delete("/{id}")
    public HttpResponse<String> delete(@QueryValue Long id) {
        final var result = dailyPlanService.delete(id);

        if (result.isError()) {
            return HttpResponse.badRequest(result.error().message());
        }

        return HttpResponse.ok();
    }
}
