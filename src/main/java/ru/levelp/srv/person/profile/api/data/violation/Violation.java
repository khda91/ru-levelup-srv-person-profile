package ru.levelp.srv.person.profile.api.data.violation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Violation {

    @Getter
    private final String description;

    @Getter
    private final Class<?> rootType;

    @Getter
    private final List<String> path = new ArrayList<>();

    public Violation(Class<?> rootType, Collection<String> path, String description) {
        this.rootType = rootType;
        if (path != null) {
            this.path.addAll(path);
        }
        this.description = description;
    }

    public abstract String getCode();
}
