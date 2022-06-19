package ru.levelp.srv.person.profile.exception;

import ru.levelp.srv.person.profile.api.exception.ResourceNotFoundProblemException;

import java.util.Map;
import java.util.UUID;

import static java.text.MessageFormat.format;

public class EntityNotFoundProblemException extends ResourceNotFoundProblemException {

    public EntityNotFoundProblemException(Class<?> entity, UUID id) {
        super(format("{0} with id: {1} does not exist", entity.getSimpleName(), id));
    }

    public EntityNotFoundProblemException(Class<?> entity, String field, String value) {
        super(format("{0} with {1}: {2} does not exist", entity.getSimpleName(), field, value));
    }

    public EntityNotFoundProblemException(Class<?> entity, Map<String, String> fieldsNameAndValue) {
        super(format("{0} with {1} does not exist", entity.getSimpleName(), fieldsNameAndValue));
    }
}
