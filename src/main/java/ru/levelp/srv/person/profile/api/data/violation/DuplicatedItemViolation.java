package ru.levelp.srv.person.profile.api.data.violation;

import java.util.Collection;

public class DuplicatedItemViolation extends Violation {

    public DuplicatedItemViolation(Class<?> rootType, Collection<String> path, String description) {
        super(rootType, path, description);
    }

    @Override
    public String getCode() {
        return "duplicated_item";
    }

}
