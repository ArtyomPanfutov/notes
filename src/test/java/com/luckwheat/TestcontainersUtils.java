package com.luckwheat;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Generic utils methods for test containers.
 * @author Artem Panfutov
 */
public final class TestcontainersUtils {

    public static PostgreSQLContainer createPostgres() {
        final var container = new PostgreSQLContainer("postgres:12")
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("postgres");

        return container;
    }

}
