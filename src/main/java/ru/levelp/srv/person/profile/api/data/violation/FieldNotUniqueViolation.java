package ru.levelp.srv.person.profile.api.data.violation;

import java.util.Collection;

public class FieldNotUniqueViolation extends Violation {

    public FieldNotUniqueViolation(Class<?> rootType, Collection<String> path, String description) {
        super(rootType, path, description);
    }

    @Override
    public String getCode() {
        return "field_not_unique";
    }
}
