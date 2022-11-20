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

    private final NoteService noteService;

    @Inject
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<Void> create(@Body NoteDto noteDto) {
        final var result = noteService.create(noteDto);

        if (!result.isSuccess()) {
            // TODO: Implement error handling
            log.error("Can't create a note {}", result.error());
            return HttpResponse.badRequest();
        }

        return HttpResponse.ok();
    }

    @Put
    public HttpResponse<Void> update(@Body NoteDto noteDto) {
        final var result = noteService.update(noteDto);

        if (result.isError()) {
            return HttpResponse.badRequest();
        }

        return HttpResponse.ok();
    }

    @Get
    public HttpResponse<List<NoteDto>> findAll() {
        return HttpResponse.ok(noteService.findAll());
    }

    @Get("/{id}")
    public HttpResponse<NoteDto> findById(@QueryValue Long id) {
        final var result = noteService.findById(id);

        if (result.isEmpty()) {
            return HttpResponse.notFound();
        }

        return HttpResponse.ok(result.get());
    }

    @Delete("/{id}")
    public HttpResponse<String> delete(@QueryValue Long id) {
        final var result = noteService.delete(id);
        if (result.isError()) {
            return HttpResponse.badRequest(result.error().message());
        }

        return HttpResponse.ok();
    }
}
