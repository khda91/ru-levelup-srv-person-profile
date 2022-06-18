package ru.levelp.srv.person.profile.api.exception;

import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.api.data.violation.ReferredItemNotFoundViolation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ReferredItemNotFoundViolationException extends ValidationProblemException {

    public ReferredItemNotFoundViolationException(Class<?> rootType, Collection<String> path, String description, Throwable th) {
        super(th);
        ReferredItemNotFoundViolation violation = new ReferredItemNotFoundViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public ReferredItemNotFoundViolationException(Class<?> rootType, Collection<String> path, String description) {
        super();
        ReferredItemNotFoundViolation violation = new ReferredItemNotFoundViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public ReferredItemNotFoundViolationException(List<ReferredItemNotFoundViolation> violations, String description) {
        super(description, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }

    public ReferredItemNotFoundViolationException(List<ReferredItemNotFoundViolation> violations, String description, Throwable th) {
        super(description, th, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }

}
