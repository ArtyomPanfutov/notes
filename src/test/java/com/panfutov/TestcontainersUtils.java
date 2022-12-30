package com.panfutov;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

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

    public static ElasticsearchContainer createElastic() {
        final var container = new ElasticsearchContainer("elasticsearch:5.6");
        container.addExposedPort(9200);
        container.addEnv("cluster.name", "elasticsearch");

        return container;
    }

}
