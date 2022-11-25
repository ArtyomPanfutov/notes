package com.luckwheat.notes.controller;

import com.luckwheat.notes.dto.GeneratedNoteNameDto;
import com.luckwheat.notes.dto.NoteContentSearchDto;
import com.luckwheat.notes.dto.NoteDto;
import com.luckwheat.notes.service.NoteNameGenerator;
import com.luckwheat.notes.service.NoteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Controller("/api/notes")
@Slf4j
public class NoteController {

    private final NoteService noteService;

    private final NoteNameGenerator noteNameGenerator;

    @Inject
    public NoteController(NoteService noteService, NoteNameGenerator noteNameGenerator) {
        this.noteService = noteService;
        this.noteNameGenerator = noteNameGenerator;
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

    @Post("/search")
    public HttpResponse<List<NoteDto>> search(@Body NoteContentSearchDto contentSearchDto) {
        var result = noteService.search(contentSearchDto.content());

        if (result.isSuccess()) {
            return HttpResponse.ok(result.body());
        }
        return HttpResponse.ok(Collections.emptyList());
    }

    @Get("/{id}")
    public HttpResponse<NoteDto> findById(@QueryValue Long id) {
        final var result = noteService.findById(id);

        if (result.isEmpty()) {
            return HttpResponse.notFound();
        }

        return HttpResponse.ok(result.get());
    }

    @Get("/name/new/generate")
    public HttpResponse<GeneratedNoteNameDto> generateNoteName() {
        return HttpResponse.ok(
                new GeneratedNoteNameDto(noteNameGenerator.generateNoteName()));
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
