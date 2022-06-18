package ru.levelp.srv.person.profile.api.exception;

import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.api.data.problem.ResourceNotFoundProblem;

import java.util.List;

public class ResourceNotFoundProblemException extends ProblemException {

    public ResourceNotFoundProblemException() {
        super(new ResourceNotFoundProblem());
    }

    public ResourceNotFoundProblemException(String message) {
        super(new ResourceNotFoundProblem(message));
    }

    public ResourceNotFoundProblemException(Throwable cause) {
        super(new ResourceNotFoundProblem(), cause);
    }

    public ResourceNotFoundProblemException(List<Violation> violations) {
        super(new ResourceNotFoundProblem(violations));
    }

    public ResourceNotFoundProblemException(String message, Throwable cause) {
        super(new ResourceNotFoundProblem(message), cause);
    }

    public ResourceNotFoundProblemException(String message, List<Violation> violations) {
        super(new ResourceNotFoundProblem(message, violations));
    }

    public ResourceNotFoundProblemException(Throwable cause, List<Violation> violations) {
        super(new ResourceNotFoundProblem(violations), cause);
    }

    public ResourceNotFoundProblemException(String message, Throwable cause, List<Violation> violations) {
        super(new ResourceNotFoundProblem(message, violations), cause);
    }

}
