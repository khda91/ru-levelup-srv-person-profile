package ru.levelp.srv.person.profile.infrastructure.config;

import org.junit.ClassRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import ru.levelp.srv.person.profile.infrastructure.container.PostgresContainer;

public class TestInitializer {

    private static final String SPRING_DATASOURCE_URL_TEMPLATE = "spring.datasource.url=%s";
    private static final String SPRING_DATASOURCE_USERNAME_TEMPLATE = "spring.datasource.username=%s";
    private static final String SPRING_DATASOURCE_PASSWORD_TEMPLATE = "spring.datasource.password=%s";

    @ClassRule
    public static PostgresContainer postgres = new PostgresContainer();

    public static class Api implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            init(context);
        }
    }

    public static class Component implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            init(context);
        }
    }

    private static void init(ConfigurableApplicationContext context) {
        postgres.start();

        TestPropertyValues.of(
                String.format(SPRING_DATASOURCE_URL_TEMPLATE, postgres.getJdbcUrl()),
                String.format(SPRING_DATASOURCE_USERNAME_TEMPLATE, postgres.getUsername()),
                String.format(SPRING_DATASOURCE_PASSWORD_TEMPLATE, postgres.getPassword())
        ).applyTo(context.getEnvironment());
    }
}
