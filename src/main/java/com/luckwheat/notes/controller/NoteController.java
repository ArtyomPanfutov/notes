package com.luckwheat.notes.controller;

import com.luckwheat.notes.dto.NoteDto;
import com.luckwheat.notes.service.NoteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller("/api/notes")
@Slf4j
public class NoteController {

    @Inject
    NoteService noteService;

    @Post(consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<Void> create(@Body NoteDto noteDto) {
        final var result = noteService.create(noteDto);

        if (!result.success()) {
            // TODO: Implement error handling
            log.error("Can't create a note {}", result.error());
            return HttpResponse.badRequest();
        }

        return HttpResponse.ok();
    }

    @Get
    public HttpResponse<List<NoteDto>> findAll() {
        return HttpResponse.ok(noteService.findAll());
    }

    @Delete("/{id}")
    public HttpResponse<String> delete(@QueryValue Long id) {
        final var result = noteService.delete(id);
        if (result.fail()) {
            return HttpResponse.badRequest(result.error().message());
        }

        return HttpResponse.ok();
    }
}
