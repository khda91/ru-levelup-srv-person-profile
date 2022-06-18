package ru.levelp.srv.person.profile.api.data.problem;

import org.springframework.http.HttpStatus;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResourceNotFoundProblem extends Problem {

    public ResourceNotFoundProblem() {
        super();
    }

    public ResourceNotFoundProblem(String description) {
        super(description);
    }

    public ResourceNotFoundProblem(List<Violation> violations) {
        super(violations);
    }

    public ResourceNotFoundProblem(String description, List<Violation> violations) {
        super(description, violations);
    }

    @Override
    protected String getCode() {
        return "resource_not_found";
    }

    @Override
    public String getTitle() {
        return HttpStatus.NOT_FOUND.getReasonPhrase();
    }

    @Override
    public String getDefaultDescription() {
        return "The requested resource could not be found";
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
