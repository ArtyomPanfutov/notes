package com.panfutov.notes.service;

import com.google.common.collect.ImmutableList;
import com.panfutov.notes.dto.Error;
import com.panfutov.notes.dto.NoteDto;
import com.panfutov.notes.dto.Result;
import com.panfutov.notes.dto.ResultPage;
import com.panfutov.notes.dto.auth0.UserInfo;
import com.panfutov.notes.entity.Note;
import com.panfutov.notes.entity.User;
import com.panfutov.notes.repository.NoteRepository;
import com.panfutov.notes.repository.ProjectRepository;
import com.panfutov.notes.service.exception.NoteServiceException;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.Search;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    private final ProjectRepository projectRepository;

    private final EntityManager entityManager;

    private final UserService userService;

    @Inject
    public NoteService(NoteRepository noteRepository, ProjectRepository projectRepository, EntityManager entityManager, UserService userService) {
        this.noteRepository = noteRepository;
        this.projectRepository = projectRepository;
        this.entityManager = entityManager;
        this.userService = userService;
    }

    @Transactional
    public Result<NoteDto> create(NoteDto noteDto, UserInfo userInfo) {
        if (noteDto == null) {
            return Result.error(new Error("Can't create a null note"));
        }

        var user = userService.getUserByUserInfo(userInfo);

        // TODO: Validation
        log.warn("Validation is not implemented yet!");

        final var entity = convertToEntity(noteDto);
        final var now = LocalDateTime.now(ZoneId.systemDefault());
        entity.setCreatedTimestamp(now);
        entity.setUpdatedTimestamp(now);
        entity.setUser(user);
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
    public ResultPage<NoteDto> findAllByUser(UserInfo userInfo, Pageable pageable) {
        var user = userService.getUserByUserInfo(userInfo);
        final var notes = noteRepository.findAllByUser(user, pageable);
        final var result = ImmutableList.<NoteDto>builder();

        for (Note note : notes) {
            result.add(convertToDto(note));
        }

        return new ResultPage<>(result.build(), notes.getPageNumber(), notes.getTotalPages());
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

    @Transactional
    public Page<NoteDto> search(String content, UserInfo userInfo, Pageable pageable) {
        var user = userService.getUserByUserInfo(userInfo);
        var fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        var queryBuilder = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Note.class)
                .get();

        var contentQuery = queryBuilder.keyword()
                .fuzzy()
                .withEditDistanceUpTo(2)
                .onField(Note.CONTENT_TRANSFORMED)
                .matching(content)
                .createQuery();

        var userQuery = queryBuilder.keyword()
                .onField("user." + User.USER_ID)
                .matching(user.getId())
                .createQuery();

        var combinedQuery = fullTextEntityManager.createFullTextQuery(
                queryBuilder.bool()
                        .must(contentQuery)
                        .must(userQuery)
                        .createQuery(),
                Note.class);

        combinedQuery.setFirstResult(pageable.getNumber() * pageable.getSize());
        combinedQuery.setMaxResults(pageable.getSize());
        combinedQuery.setSort(queryBuilder.sort().byScore().createSort());

        List<Note> resultList = combinedQuery.getResultList();
        return Page.of(resultList.stream().map(this::convertToDto).toList(), pageable, combinedQuery.getResultSize());
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
