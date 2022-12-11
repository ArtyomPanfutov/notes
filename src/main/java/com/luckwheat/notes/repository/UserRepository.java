package com.luckwheat.notes.repository;

import com.luckwheat.notes.entity.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findBySub(String sub);

    boolean existsBySub(String sub);
}
