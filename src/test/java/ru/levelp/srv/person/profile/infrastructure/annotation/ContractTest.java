package ru.levelp.srv.person.profile.infrastructure.annotation;

import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@DisabledIfSystemProperty(named = SkipFlags.SKIP_CONTRACT_TESTS, matches = "true")
@ExtendWith(SpringExtension.class)
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Inherited
public @interface ContractTest {
}
