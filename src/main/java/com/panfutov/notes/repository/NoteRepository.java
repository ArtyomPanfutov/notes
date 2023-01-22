package com.panfutov.notes.repository;

import com.panfutov.notes.entity.Note;
import com.panfutov.notes.entity.Project;
import com.panfutov.notes.entity.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    boolean existsByNameAndUser(String name, User user);

    boolean existsByProject(Project project);

    Page<Note> findAllByUser(User user, Pageable pageable);

}
