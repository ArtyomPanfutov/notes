package com.panfutov.notes.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;

import static io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS;

@Controller("/api/health")
@Secured(IS_ANONYMOUS)
public class HealthController {

    @Get
    public HttpResponse<Void> health() {
        // TODO
        return HttpResponse.ok();
    }
}
