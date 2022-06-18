package ru.levelp.srv.person.profile.api.data.violation;

import java.util.Collection;

public class FieldCannotBeUpdatedViolation extends Violation {

    public FieldCannotBeUpdatedViolation(Class<?> rootType, Collection<String> path, String description) {
        super(rootType, path, description);
    }

    @Override
    public String getCode() {
        return "field_cannot_be_updated";
    }
}
