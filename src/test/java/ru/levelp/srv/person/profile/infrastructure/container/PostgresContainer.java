package ru.levelp.srv.person.profile.infrastructure.container;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainer extends PostgreSQLContainer<PostgresContainer> {

    private static final String IMAGE_NAME = "postgres:14.3";
    private static final String DEFAULT_DATABASE = "database";
    private static final String DEFAULT_USER = "developer";
    private static final String DEFAULT_PASSWORD = "password";

    public PostgresContainer() {
        super(IMAGE_NAME);
        withDatabaseName(DEFAULT_DATABASE);
        withUsername(DEFAULT_USER);
        withPassword(DEFAULT_PASSWORD);
    }
}
