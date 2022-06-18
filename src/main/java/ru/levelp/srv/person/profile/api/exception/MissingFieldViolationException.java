package ru.levelp.srv.person.profile.api.exception;

import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.api.data.violation.MissingFieldViolation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MissingFieldViolationException extends ValidationProblemException {

    public MissingFieldViolationException(Class<?> rootType, Collection<String> path, String description, Throwable th) {
        super(th);
        MissingFieldViolation violation = new MissingFieldViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public MissingFieldViolationException(Class<?> rootType, Collection<String> path, String description) {
        super();
        MissingFieldViolation violation = new MissingFieldViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public MissingFieldViolationException(List<MissingFieldViolation> violations, String description) {
        super(description, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }

    public MissingFieldViolationException(List<MissingFieldViolation> violations, String description, Throwable th) {
        super(description, th, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }
}
