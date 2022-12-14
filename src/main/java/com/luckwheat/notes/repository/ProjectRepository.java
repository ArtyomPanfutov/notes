package com.luckwheat.notes.repository;

import com.luckwheat.notes.entity.Project;
import com.luckwheat.notes.entity.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Optional<Project> findByNameAndUser(String name, User user);

    Page<Project> findAllByUser(User user, Pageable pageable);
}
