package com.panfutov.notes.repository;

import com.panfutov.notes.entity.DailyPlan;
import com.panfutov.notes.entity.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface DailyPlanRepository extends CrudRepository<DailyPlan, Long> {
    Page<DailyPlan> findAllByUser(User user, Pageable pageable);
}
