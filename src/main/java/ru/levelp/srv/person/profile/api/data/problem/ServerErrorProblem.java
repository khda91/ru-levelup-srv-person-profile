package ru.levelp.srv.person.profile.api.data.problem;

import org.springframework.http.HttpStatus;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.List;

public class ServerErrorProblem extends Problem {

    public ServerErrorProblem() {
        super();
    }

    public ServerErrorProblem(String description) {
        super(description);
    }

    public ServerErrorProblem(List<Violation> violations) {
        super(violations);
    }

    public ServerErrorProblem(String description, List<Violation> violations) {
        super(description, violations);
    }

    @Override
    protected String getCode() {
        return "server_error";
    }

    @Override
    public String getTitle() {
        return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }

    @Override
    public String getDefaultDescription() {
        return "Unexpected exception occurred";
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
