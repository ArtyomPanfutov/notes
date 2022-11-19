package com.luckwheat.notes.service;

import com.google.common.collect.ImmutableList;
import com.luckwheat.notes.dto.Error;
import com.luckwheat.notes.dto.NoteDto;
import com.luckwheat.notes.dto.Result;
import com.luckwheat.notes.entity.Note;
import com.luckwheat.notes.repository.NoteRepository;
import com.luckwheat.notes.repository.ProjectRepository;
import com.luckwheat.notes.service.exception.NoteServiceException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public class NoteService {

    @Inject
    NoteRepository noteRepository;

    @Inject
    ProjectRepository projectRepository;

    @Transactional
    public Result<NoteDto> create(NoteDto noteDto) {
        if (noteDto == null) {
            return Result.error(new Error("Can't create a null note"));
        }

        // TODO: Validation
        log.warn("Validation is not implemented yet!");

        final var entity = convertToEntity(noteDto);
        final var now = LocalDateTime.now(ZoneId.systemDefault());
        entity.setCreatedTimestamp(now);
        entity.setUpdatedTimestamp(now);
        final var note = noteRepository.save(entity);

        return Result.success(convertToDto(note));
    }

    @Transactional
    public Result<NoteDto> update(NoteDto noteDto) {
        if (noteDto == null || noteDto.id() == null) {
            return Result.error(new Error("Can't update null note"));
        }

        // TODO: Validation
        log.warn("Validation is not implemented yet!");

        final var entity = noteRepository.findById(noteDto.id());
        if (entity.isPresent()) {
            final var note = entity.get();
            note.setName(noteDto.name());

            var project = projectRepository.findById(noteDto.projectId())
                    .orElseThrow(() -> new NoteServiceException("Project not found"));

            note.setProject(project);
            note.setContent(noteDto.content());
            note.setUpdatedTimestamp(LocalDateTime.now(ZoneId.systemDefault()));

            final var updated = noteRepository.save(note);
            return Result.success(convertToDto(updated));
        } else {
            return Result.error(new Error("Note is not found"));
        }
    }

    @Transactional
    public List<NoteDto> findAll() {
        final var notes = noteRepository.findAll();
        final var result = ImmutableList.<NoteDto>builder();

        for (Note note : notes) {
            result.add(convertToDto(note));
        }

        return result.build();
    }

    @Transactional
    public Optional<NoteDto> findById(Long id) {
        return noteRepository.findById(id)
                .map(this::convertToDto);

    }

    @Transactional
    public Result<Void> delete(Long id) {
        final var note = noteRepository.findById(id);
        if (note.isEmpty()) {
            return Result.error(new Error("Note doesn't exist"));
        }
        noteRepository.deleteById(id);

        return Result.successVoid();
    }

    private NoteDto convertToDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getName(),
                note.getContent(),
                note.getCreatedTimestamp(),
                note.getUpdatedTimestamp(),
                note.getProject().getId());
    }

    private Note convertToEntity(NoteDto noteDto) {
        final var note = new Note();

        note.setId(noteDto.id());
        note.setName(noteDto.name());
        note.setContent(noteDto.content());
        note.setCreatedTimestamp(noteDto.createdTimestamp());
        note.setUpdatedTimestamp(noteDto.updatedTimestamp());

        final var project = projectRepository.findById(noteDto.projectId()).orElseThrow();

        note.setProject(project);

        return note;
    }
}
