package ru.levelp.srv.person.profile.api.data.problem;

import org.springframework.http.HttpStatus;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.List;

public class ConflictProblem extends Problem {

    public ConflictProblem() {
        super();
    }

    public ConflictProblem(String description) {
        super(description);
    }

    public ConflictProblem(List<Violation> violations) {
        super(violations);
    }

    public ConflictProblem(String description, List<Violation> violations) {
        super(description, violations);
    }

    @Override
    protected String getCode() {
        return "conflict";
    }

    @Override
    public String getTitle() {
        return HttpStatus.CONFLICT.getReasonPhrase();
    }

    @Override
    public String getDefaultDescription() {
        return "The request could not be completed due to a conflict with the current state of the target resource";
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }

}
