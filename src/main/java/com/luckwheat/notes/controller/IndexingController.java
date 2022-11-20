package com.luckwheat.notes.controller;

import com.luckwheat.notes.service.IndexingService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/api/indexing")
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
