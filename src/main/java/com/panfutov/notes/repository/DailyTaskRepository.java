package com.panfutov.notes.repository;

import com.panfutov.notes.entity.DailyTask;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface DailyTaskRepository extends CrudRepository<DailyTask, Long> {
}
