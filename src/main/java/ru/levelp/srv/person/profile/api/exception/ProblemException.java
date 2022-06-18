package ru.levelp.srv.person.profile.api.exception;

import lombok.Getter;
import ru.levelp.srv.person.profile.api.data.problem.Problem;

public abstract class ProblemException extends RuntimeException {

    @Getter
    protected final Problem problem;

    public ProblemException(Problem problem) {
        super();
        this.problem = problem;
    }

    public ProblemException(Problem problem, Throwable cause) {
        super(cause);
        this.problem = problem;
    }
}
