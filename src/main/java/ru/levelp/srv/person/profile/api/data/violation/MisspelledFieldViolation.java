package ru.levelp.srv.person.profile.api.data.violation;

import java.util.Collection;

public class MisspelledFieldViolation extends Violation {

    public MisspelledFieldViolation(Class<?> rootType, Collection<String> path, String description) {
        super(rootType, path, description);
    }

    @Override
    public String getCode() {
        return "misspelled_field";
    }
}
