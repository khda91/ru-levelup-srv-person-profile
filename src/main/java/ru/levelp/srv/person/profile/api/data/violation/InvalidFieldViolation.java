package ru.levelp.srv.person.profile.api.data.violation;

import java.util.Collection;

public class InvalidFieldViolation extends Violation {

    public InvalidFieldViolation(Class<?> rootType, Collection<String> path, String description) {
        super(rootType, path, description);
    }

    @Override
    public String getCode() {
        return "invalid_field";
    }
}
