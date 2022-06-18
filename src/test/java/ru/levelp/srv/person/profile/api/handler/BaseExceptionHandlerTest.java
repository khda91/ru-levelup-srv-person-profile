package ru.levelp.srv.person.profile.api.handler;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.levelp.srv.person.profile.api.data.ProblemResponse;
import ru.levelp.srv.person.profile.api.data.ViolationData;
import ru.levelp.srv.person.profile.api.data.problem.Problem;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@UnitTest
@DisplayName("BaseExceptionHandler Test")
class BaseExceptionHandlerTest {

    private BaseExceptionHandler handler = new BaseExceptionHandler() {
    };

    @Test
    @DisplayName("should catch primary root cause")
    void rootCauseForPrimaryThrowable() {
        Throwable th = new Throwable("Root");
        Throwable rootCause = handler.rootCause(th);
        assertThat(rootCause).isSameAs(th);
    }

    @Test
    @DisplayName("should catch chain of exceptions")
    void rootCauseForChainOfThrowable() {
        Throwable th1 = new Throwable("Root");
        Throwable th2 = new Throwable(th1);
        Throwable th3 = new Throwable(th2);

        Throwable rootCause = handler.rootCause(th3);

        assertThat(rootCause).isSameAs(th1)
                .isNotSameAs(th2)
                .isNotSameAs(th3);
    }

    @Test
    @DisplayName("should return response without violations")
    void responseWithNoViolations() {
        Throwable th = new Throwable();
        Problem problem = mockProblem();

        ResponseEntity<ProblemResponse> response = handler.response(problem, th);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        List<ViolationData> violations = response.getBody().getViolations();
        assertThat(violations).isNull();
    }

    @Test
    @DisplayName("should return response with violations")
    void responseWithViolations() {
        Violation v1 = new ViolationTestImpl();
        Violation v2 = new ViolationTestImpl(Object.class);

        Throwable th = new Throwable();

        Problem problem = mockProblem();
        when(problem.getViolations()).thenReturn(List.of(v1, v2));

        ResponseEntity<ProblemResponse> response = handler.response(problem, th);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        List<ViolationData> violations = response.getBody().getViolations();
        assertThat(violations).isNotNull()
                .hasSize(2);
    }

    private Problem mockProblem() {
        Problem problem = mock(Problem.class);
        when(problem.getTitle()).thenReturn("Problem");
        when(problem.getDescription()).thenReturn("Testing");
        when(problem.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        return problem;
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = true)
    private static final class ViolationTestImpl extends Violation {
        private String code;

        private ViolationTestImpl() {
            super(null, null, null);
        }

        private ViolationTestImpl(Class<?> rootType) {
            super(rootType, null, "description");
        }
    }
}
