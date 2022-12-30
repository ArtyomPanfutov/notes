package com.panfutov.notes.controller;

import com.panfutov.notes.service.IndexingService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/api/indexing")
@Secured(IS_AUTHENTICATED)
public class IndexingController {

    private final IndexingService indexingService;

    @Inject
    public IndexingController(IndexingService indexingService) {
        this.indexingService = indexingService;
    }

    @Post
    public HttpResponse<Void> startIndexing() {
        indexingService.initiateIndexing();

        return HttpResponse.ok();
    }
}
