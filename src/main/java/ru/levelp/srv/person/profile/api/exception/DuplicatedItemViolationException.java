package ru.levelp.srv.person.profile.api.exception;


import ru.levelp.srv.person.profile.api.data.violation.DuplicatedItemViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DuplicatedItemViolationException extends ConflictProblemException {

    public DuplicatedItemViolationException(Class<?> rootType, Collection<String> path, String description, Throwable th) {
        super(th);
        DuplicatedItemViolation violation = new DuplicatedItemViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public DuplicatedItemViolationException(Class<?> rootType, Collection<String> path, String description) {
        super();
        DuplicatedItemViolation violation = new DuplicatedItemViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public DuplicatedItemViolationException(List<DuplicatedItemViolation> violations, String description) {
        super(description, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }

    public DuplicatedItemViolationException(List<DuplicatedItemViolation> violations, String description, Throwable th) {
        super(description, th, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }
}
