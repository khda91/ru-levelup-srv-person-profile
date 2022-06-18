package ru.levelp.srv.person.profile.api.exception;

import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.api.data.violation.MisspelledFieldViolation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MisspelledFieldViolationException extends ValidationProblemException {

    public MisspelledFieldViolationException(Class<?> rootType, Collection<String> path, String description, Throwable th) {
        super(th);
        MisspelledFieldViolation violation = new MisspelledFieldViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public MisspelledFieldViolationException(Class<?> rootType, Collection<String> path, String description) {
        super();
        MisspelledFieldViolation violation = new MisspelledFieldViolation(rootType, path, description);
        this.problem.addViolation(violation);
    }

    public MisspelledFieldViolationException(List<MisspelledFieldViolation> violations, String description) {
        super(description, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }

    public MisspelledFieldViolationException(List<MisspelledFieldViolation> violations, String description, Throwable th) {
        super(description, th, violations.stream().map(Violation.class::cast).collect(Collectors.toList()));
    }
}
