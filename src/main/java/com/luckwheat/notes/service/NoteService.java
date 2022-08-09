package com.luckwheat.notes.service;

import com.google.common.collect.ImmutableList;
import com.luckwheat.notes.dto.CreateResult;
import com.luckwheat.notes.dto.Error;
import com.luckwheat.notes.dto.NoteDto;
import com.luckwheat.notes.entity.Note;
import com.luckwheat.notes.repository.NoteRepository;
import com.luckwheat.notes.repository.ProjectRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Singleton
@Slf4j
public class NoteService {

    @Inject
    NoteRepository noteRepository;

    @Inject
    ProjectRepository projectRepository;

    @Transactional
    public CreateResult<NoteDto> create(NoteDto noteDto) {
        if (noteDto == null) {
            return CreateResult.error(new Error("Can't create a null note"));
        }

        // TODO: Validation
        log.warn("Validation is not implemented yet!");

        final var entity = convertToEntity(noteDto);
        final var now = LocalDateTime.now(ZoneId.systemDefault());
        entity.setCreatedTimestamp(now);
        entity.setUpdatedTimestamp(now);
        final var note = noteRepository.save(entity);

        return CreateResult.success(convertToDto(note));
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
