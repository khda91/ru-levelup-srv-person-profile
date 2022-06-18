package ru.levelp.srv.person.profile.api.exception;


import ru.levelp.srv.person.profile.api.data.violation.FieldCannotBeUpdatedViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FieldCannotBeUpdatedViolationException extends ValidationProblemException {

    public FieldCannotBeUpdatedViolationException(Class<?> rootType, Collection<String> path, String description, Throwable th) {
        super(th);
        FieldCannotBeUpdatedViolation violation = new FieldCannotBeUpdatedViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public FieldCannotBeUpdatedViolationException(Class<?> rootType, Collection<String> path, String description) {
        super();
        FieldCannotBeUpdatedViolation violation = new FieldCannotBeUpdatedViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public FieldCannotBeUpdatedViolationException(List<FieldCannotBeUpdatedViolation> violations, String description) {
        super(description, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }

    public FieldCannotBeUpdatedViolationException(List<FieldCannotBeUpdatedViolation> violations, String description, Throwable th) {
        super(description, th, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }

}
