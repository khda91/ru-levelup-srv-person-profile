package ru.levelp.srv.person.profile.api.exception;


import ru.levelp.srv.person.profile.api.data.violation.InvalidFieldViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InvalidFieldViolationException extends ValidationProblemException {

    public InvalidFieldViolationException(Class<?> rootType, Collection<String> path, String description, Throwable th) {
        super(th);
        InvalidFieldViolation violation = new InvalidFieldViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public InvalidFieldViolationException(Class<?> rootType, Collection<String> path, String description) {
        super();
        InvalidFieldViolation violation = new InvalidFieldViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public InvalidFieldViolationException(List<InvalidFieldViolation> violations, String description) {
        super(description, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }

    public InvalidFieldViolationException(List<InvalidFieldViolation> violations, String description, Throwable th) {
        super(description, th, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }
}
