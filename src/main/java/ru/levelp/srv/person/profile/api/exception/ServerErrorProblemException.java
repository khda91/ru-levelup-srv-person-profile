package ru.levelp.srv.person.profile.api.exception;

import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.api.data.problem.ServerErrorProblem;

import java.util.List;

public class ServerErrorProblemException extends ProblemException {

    public ServerErrorProblemException() {
        super(new ServerErrorProblem());
    }

    public ServerErrorProblemException(String message) {
        super(new ServerErrorProblem(message));
    }

    public ServerErrorProblemException(Throwable cause) {
        super(new ServerErrorProblem(), cause);
    }

    public ServerErrorProblemException(List<Violation> violations) {
        super(new ServerErrorProblem(violations));
    }

    public ServerErrorProblemException(String message, Throwable cause) {
        super(new ServerErrorProblem(message), cause);
    }

    public ServerErrorProblemException(String message, List<Violation> violations) {
        super(new ServerErrorProblem(message, violations));
    }

    public ServerErrorProblemException(Throwable cause, List<Violation> violations) {
        super(new ServerErrorProblem(violations), cause);
    }

    public ServerErrorProblemException(String message, Throwable cause, List<Violation> violations) {
        super(new ServerErrorProblem(message, violations), cause);
    }

}
