package ru.levelp.srv.person.profile.api.data.violation;

import java.util.Collection;

public class ReferredItemNotFoundViolation extends Violation {

    public ReferredItemNotFoundViolation(Class<?> rootType, Collection<String> path, String description) {
        super(rootType, path, description);
    }

    @Override
    public String getCode() {
        return "referred_item_not_found";
    }
}
