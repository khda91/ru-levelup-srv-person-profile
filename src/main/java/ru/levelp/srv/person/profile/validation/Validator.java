package ru.levelp.srv.person.profile.validation;

import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.Optional;

/**
 * Generic validator
 *
 * @param <T> type to validate
 */
public interface Validator<T> {

    Optional<Violation> validate(T object);
}
