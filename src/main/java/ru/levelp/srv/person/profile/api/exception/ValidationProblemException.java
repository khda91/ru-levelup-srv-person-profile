package ru.levelp.srv.person.profile.api.exception;

import ru.levelp.srv.person.profile.api.data.problem.ValidationProblem;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.List;

public class ValidationProblemException extends ProblemException {

    public ValidationProblemException() {
        super(new ValidationProblem());
    }

    public ValidationProblemException(String message) {
        super(new ValidationProblem(message));
    }

    public ValidationProblemException(Throwable cause) {
        super(new ValidationProblem(), cause);
    }

    public ValidationProblemException(List<Violation> violations) {
        super(new ValidationProblem(violations));
    }

    public ValidationProblemException(String message, Throwable cause) {
        super(new ValidationProblem(message), cause);
    }

    public ValidationProblemException(String message, List<Violation> violations) {
        super(new ValidationProblem(message, violations));
    }

    public ValidationProblemException(Throwable cause, List<Violation> violations) {
        super(new ValidationProblem(violations), cause);
    }

    public ValidationProblemException(String message, Throwable cause, List<Violation> violations) {
        super(new ValidationProblem(message, violations), cause);
    }

}
