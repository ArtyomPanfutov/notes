package com.panfutov.notes.controller;

import com.panfutov.notes.dto.GeneratedNoteNameDto;
import com.panfutov.notes.dto.NoteContentSearchDto;
import com.panfutov.notes.dto.NoteDto;
import com.panfutov.notes.dto.ResultPage;
import com.panfutov.notes.service.NoteNameGenerator;
import com.panfutov.notes.service.NoteService;
import com.panfutov.notes.service.UserService;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import static io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED;

@Controller("/api/notes")
@Secured(IS_AUTHENTICATED)
@Slf4j
public class NoteController {

    private final NoteService noteService;

    private final UserService userService;

    private final NoteNameGenerator noteNameGenerator;

    @Inject
    public NoteController(NoteService noteService, UserService userService, NoteNameGenerator noteNameGenerator) {
        this.noteService = noteService;
        this.userService = userService;
        this.noteNameGenerator = noteNameGenerator;
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<Void> create(@Body NoteDto noteDto, @Header("Authorization") String authorization) {
        var user = userService.getUserByBearerToken(authorization);
        final var result = noteService.create(noteDto, user);

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
    public HttpResponse<ResultPage<NoteDto>> findAll(@Header("Authorization") String authorization,
                                                     @QueryValue int page,
                                                     @QueryValue int pageSize) {
        var user = userService.getUserByBearerToken(authorization);
        return HttpResponse.ok(noteService.findAllByUser(user, Pageable.from(page, pageSize)));
    }

    @Post("/search")
    public HttpResponse<ResultPage<NoteDto>> search(@Body NoteContentSearchDto contentSearchDto,
                                                    @Header("Authorization") String authorization) {
        var user = userService.getUserByBearerToken(authorization);
        var result = noteService.search(
                contentSearchDto.content(),
                user,
                Pageable.from(contentSearchDto.page(), contentSearchDto.pageSize()));

        return HttpResponse.ok(
                new ResultPage<>(result.getContent(), result.getPageNumber(), result.getTotalPages()));
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
    public HttpResponse<GeneratedNoteNameDto> generateNoteName(@Header("Authorization") String authorization) {
        var user = userService.getUserByBearerToken(authorization);
        return HttpResponse.ok(
                new GeneratedNoteNameDto(noteNameGenerator.generateNoteName(user)));
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
