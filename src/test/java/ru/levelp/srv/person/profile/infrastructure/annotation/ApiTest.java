package ru.levelp.srv.person.profile.infrastructure.annotation;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.levelp.srv.person.profile.infrastructure.config.TestConfig;
import ru.levelp.srv.person.profile.infrastructure.config.TestInitializer;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@DisabledIfSystemProperty(named = SkipFlags.SKIP_API_TESTS, matches = "true")
@ExtendWith(SpringExtension.class)
@Testcontainers
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(initializers = {TestInitializer.Api.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Inherited
public @interface ApiTest {
}
