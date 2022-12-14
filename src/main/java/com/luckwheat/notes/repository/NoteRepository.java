package com.luckwheat.notes.repository;

import com.luckwheat.notes.entity.Note;
import com.luckwheat.notes.entity.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    boolean existsByNameAndUser(String name, User user);

    Page<Note> findAllByUser(User user, Pageable pageable);

}
