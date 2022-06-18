package ru.levelp.srv.person.profile.validation;

import org.springframework.stereotype.Service;
import ru.levelp.srv.person.profile.api.exception.ValidationProblemException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValidationUtils {

    public <T, V extends Validator<T>> void validate(T object, List<V> validators) {
        var violations = validators.stream()
                .flatMap(v -> v.validate(object).stream())
                .collect(Collectors.toList());

        if (!violations.isEmpty()) {
            throw new ValidationProblemException(violations);
        }
    }
}
