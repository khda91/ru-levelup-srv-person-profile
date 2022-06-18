package ru.levelp.srv.person.profile.api.data.problem;

import org.springframework.http.HttpStatus;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.List;

public class ValidationProblem extends Problem {

    public ValidationProblem() {
        super();
    }

    public ValidationProblem(String description) {
        super(description);
    }

    public ValidationProblem(List<Violation> violations) {
        super(violations);
    }

    public ValidationProblem(String description, List<Violation> violations) {
        super(description, violations);
    }

    @Override
    protected String getCode() {
        return "validation";
    }

    @Override
    public String getTitle() {
        return HttpStatus.BAD_REQUEST.getReasonPhrase();
    }

    @Override
    protected String getDefaultDescription() {
        return "A malformed request was sent";
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
