package ru.levelp.srv.person.profile.api.data.violation;

import java.util.Collection;

public class MissingFieldViolation extends Violation {

    public MissingFieldViolation(Class<?> rootType, Collection<String> path, String description) {
        super(rootType, path, description);
    }

    @Override
    public String getCode() {
        return "missing_field";
    }
}
