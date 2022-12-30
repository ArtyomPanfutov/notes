package com.panfutov.notes.repository;

import com.panfutov.notes.entity.Project;
import com.panfutov.notes.entity.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Optional<Project> findByNameAndUser(String name, User user);

    Page<Project> findAllByUser(User user, Pageable pageable);

    List<Project> findAllByUser(User user);
}
