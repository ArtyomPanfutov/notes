package com.luckwheat.notes.repository;

import com.luckwheat.notes.entity.Note;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    boolean existsByName(String name);

}
