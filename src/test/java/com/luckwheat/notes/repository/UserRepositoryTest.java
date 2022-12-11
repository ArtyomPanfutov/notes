package com.luckwheat.notes.repository;

import com.luckwheat.notes.entity.User;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@MicronautTest
class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    void testCreate() {
        // GIVEN
        var user = new User();
        user.setSub("auth0-sub");

        // WHEN
        var saved = userRepository.save(user);

        // THEN
        assertEquals(user.getSub(), saved.getSub());
    }

    @Test
    void testExistByAuthSub() {
        // GIVEN
        var auth0Sub = "1010";
        createUser(auth0Sub);

        // WHEN
        var exists = userRepository.existsBySub(auth0Sub);

        // THEN
        assertTrue(exists);
    }

    @Test
    void testNotExistByAuthSub() {
        // WHEN
        var exists = userRepository.existsBySub("");

        // THEN
        assertFalse(exists);
    }

    @Test
    void testFindByAuthSub() {
        // GIVEN
        var auth0Sub = "1010";
        createUser(auth0Sub);

        // WHEN
        var user = userRepository.findBySub(auth0Sub);

        // THEN
        assertTrue(user.isPresent());
        assertEquals(user.get().getSub(), auth0Sub);
    }

    private void createUser(String auth0Sub) {
        var user = new User();
        user.setSub(auth0Sub);
        userRepository.save(user);
    }

}
