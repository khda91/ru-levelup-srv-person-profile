package ru.levelp.srv.person.profile.api.data.problem;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.levelp.srv.person.profile.api.data.violation.Violation;

import java.util.ArrayList;
import java.util.List;

public abstract class Problem {

    @Getter
    private final String description;

    @Getter
    private final List<Violation> violations;

    public Problem() {
        this.description = getDefaultDescription();
        this.violations = new ArrayList<>();
    }

    public Problem(String description) {
        this.description = description;
        this.violations = new ArrayList<>();
    }

    public Problem(List<Violation> violations) {
        this.description = getDefaultDescription();
        this.violations = violations;
    }

    public Problem(String description, List<Violation> violations) {
        this.description = description;
        this.violations = violations;
    }

    public String getType() {
        return getCode();
    }

    protected abstract String getCode();

    public abstract String getTitle();

    protected abstract String getDefaultDescription();

    public abstract HttpStatus getStatus();

    public void addViolation(Violation violation) {
        violations.add(violation);
    }
}
