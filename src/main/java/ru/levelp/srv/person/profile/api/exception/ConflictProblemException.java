package ru.levelp.srv.person.profile.api.exception;


import ru.levelp.srv.person.profile.api.data.problem.ConflictProblem;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.List;

public class ConflictProblemException extends ProblemException {

    public ConflictProblemException() {
        super(new ConflictProblem());
    }

    public ConflictProblemException(String message) {
        super(new ConflictProblem(message));
    }

    public ConflictProblemException(Throwable cause) {
        super(new ConflictProblem(), cause);
    }

    public ConflictProblemException(List<Violation> violations) {
        super(new ConflictProblem(violations));
    }

    public ConflictProblemException(String message, Throwable cause) {
        super(new ConflictProblem(message), cause);
    }

    public ConflictProblemException(String message, List<Violation> violations) {
        super(new ConflictProblem(message, violations));
    }

    public ConflictProblemException(Throwable cause, List<Violation> violations) {
        super(new ConflictProblem(violations), cause);
    }

    public ConflictProblemException(String message, Throwable cause, List<Violation> violations) {
        super(new ConflictProblem(message, violations), cause);
    }

}
