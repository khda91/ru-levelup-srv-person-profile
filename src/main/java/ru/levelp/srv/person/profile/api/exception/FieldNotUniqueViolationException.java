package ru.levelp.srv.person.profile.api.exception;


import ru.levelp.srv.person.profile.api.data.violation.FieldNotUniqueViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FieldNotUniqueViolationException extends ValidationProblemException {

    public FieldNotUniqueViolationException(Class<?> rootType, Collection<String> path, String description, Throwable th) {
        super(th);
        FieldNotUniqueViolation violation = new FieldNotUniqueViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public FieldNotUniqueViolationException(Class<?> rootType, Collection<String> path, String description) {
        super();
        FieldNotUniqueViolation violation = new FieldNotUniqueViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public FieldNotUniqueViolationException(List<FieldNotUniqueViolation> violations, String description) {
        super(description, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }

    public FieldNotUniqueViolationException(List<FieldNotUniqueViolation> violations, String description, Throwable th) {
        super(description, th, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }
}
