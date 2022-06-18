package ru.levelp.srv.person.profile.api.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.levelp.srv.person.profile.api.data.ProblemResponse;
import ru.levelp.srv.person.profile.api.data.ViolationData;
import ru.levelp.srv.person.profile.api.data.problem.Problem;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class BaseExceptionHandler {

    protected Throwable rootCause(Throwable th) {
        if (th.getCause() == null) {
            return th;
        } else {
            return rootCause(th.getCause());
        }
    }

    protected ResponseEntity<ProblemResponse> response(Problem problem, Throwable th) {
        log.error("{} ({})", problem.getTitle(), problem.getDescription(), th);

        ProblemResponse response = new ProblemResponse();
        response.setTitle(problem.getTitle());
        response.setDetail(problem.getDescription());
        response.setStatus(Integer.toString(problem.getStatus().value()));
        response.setType(problem.getType());

        if (!problem.getViolations().isEmpty()) {
            List<ViolationData> violations = new ArrayList<>();

            for (Violation violation : problem.getViolations()) {
                var data = new ViolationData();
                data.setCode(violation.getCode());
                data.setDetail(violation.getDescription());

                var path = String.join(".", violation.getPath());
                if (violation.getRootType() != null) {
                    path = violation.getRootType().getSimpleName() + "." + path;
                }

                data.setField(path);
                violations.add(data);
            }
            response.setViolations(violations);
        }

        var contentType = MediaType.APPLICATION_PROBLEM_JSON;
        var headers = new HttpHeaders();
        headers.setContentType(contentType);

        return new ResponseEntity<>(response, headers, problem.getStatus());
    }
}
