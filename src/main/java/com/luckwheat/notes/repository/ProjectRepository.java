package com.luckwheat.notes.repository;

import com.luckwheat.notes.entity.Project;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
}
