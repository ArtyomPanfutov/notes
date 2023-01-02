package com.panfutov.notes.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/api/health")
public class HealthController {

    @Get
    public HttpResponse<Void> getDefaultProject() {
        // TODO
        return HttpResponse.ok();
    }
}
