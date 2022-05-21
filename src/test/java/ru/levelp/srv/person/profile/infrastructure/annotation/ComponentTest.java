package ru.levelp.srv.person.profile.infrastructure.annotation;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener;
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

@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(initializers = {TestInitializer.Component.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        MockitoTestExecutionListener.class,
        ResetMocksTestExecutionListener.class
})
@DisabledIfSystemProperty(named = SkipFlags.SKIP_COMPONENT_TESTS, matches = "true")
@ExtendWith(SpringExtension.class)
@Testcontainers
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Inherited
public @interface ComponentTest {
}
