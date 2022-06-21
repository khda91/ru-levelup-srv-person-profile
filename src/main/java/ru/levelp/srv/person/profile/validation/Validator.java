package ru.levelp.srv.person.profile.validation;

import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Generic validator
 *
 * @param <T> type to validate
 */
public interface Validator<T> {

    /**
     * Returns Violation object in case of validation error
     *
     * @param object object to validate
     */
    Optional<Violation> validate(T object);

    default Collection<String> convertPath(String path) {
        return Arrays.asList(path.split("\\."));
    }
}
