package ru.levelp.srv.person.profile.api.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.levelp.srv.person.profile.api.data.ProblemResponse;
import ru.levelp.srv.person.profile.api.data.problem.Problem;
import ru.levelp.srv.person.profile.api.data.problem.ResourceNotFoundProblem;
import ru.levelp.srv.person.profile.api.data.problem.ServerErrorProblem;
import ru.levelp.srv.person.profile.api.data.problem.ValidationProblem;
import ru.levelp.srv.person.profile.api.data.violation.FieldCannotBeUpdatedViolation;
import ru.levelp.srv.person.profile.api.data.violation.InvalidFieldViolation;
import ru.levelp.srv.person.profile.api.data.violation.MissingFieldViolation;
import ru.levelp.srv.person.profile.api.data.violation.Violation;
import ru.levelp.srv.person.profile.api.exception.MissingFieldViolationException;
import ru.levelp.srv.person.profile.api.exception.ProblemException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler extends BaseExceptionHandler {

    /*
        How to produce:
        Just throw some runtime exception somewhere in model
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ProblemResponse> handle(Exception exception) {
        var problem = new ServerErrorProblem(exception.getMessage());
        return response(problem, exception);
    }

    /*
      How to produce:
      Make request to non-existent resource. For example, localhost:8080/my-base-path/abracadabra
      server.servlet.contextPath doesn't count
      https://stackoverflow.com/questions/46385452/spring-mvc-how-to-handle-incoming-request-to-wrong-context-path
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<ProblemResponse> handle(NoHandlerFoundException ex) {
        var problem = new ResourceNotFoundProblem(ex.getMessage());
        return response(problem, ex);
    }

    /*
        How to produce:
        Make request to some resource with unsupported HTTP method type for this resource. For example,
        if there is GET handler for route localhost:8080/my-base-path/some-route, then make PUT request on this route
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemResponse> handle(HttpRequestMethodNotSupportedException ex) {
        var problem = new ResourceNotFoundProblem(ex.getMessage());
        return response(problem, ex);
    }

    /*
      How to produce:
      Make GET request with invalid value for string path parameter with some regex pattern. For example, regex pattern for parameter is UUID, but request value is ABRACADABRA
      Or make POST request with empty value for object with NotNull constraint. For example body is {"someObject": null}
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ProblemResponse> handle(ConstraintViolationException ex) {
        var problem = new ValidationProblem();

        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
            var message = error.getMessage();

            // We have two cases for a Constraint Violation: 1 - invalid parameter in URL, 2 - invalid field in request body.
            //
            // For the 1st case error.getPropertyPath() will return not only an invalid parameter name but as first path element it will return
            // a controller method name which we SHOULD NOT to send with response (Example method call result: getPerson.personId).
            //
            // For the 2nd case error.getPropertyPath() will return only path to the invalid element in request body but we should extend it
            // with request body model name as first element (Example method call result: identity.firstName)
            //
            // So to divide this two cases we can use another method - error.getRootBeanClass()
            // For the 1st case this method returns a controller class, where we have an error (Example: PeopleApiController)
            // For the 2nd case this method returns a request body model class (Example: UpdatePersonData)
            //
            // All our controllers are annotated with @RestController annotation, so knowing the above, to define which of these two cases do we have
            // we need just to check this annotation on a result of error.getRootBeanClass()
            //
            // 1: annotation present -> error from controller -> remove first path element (Final result example: personId)
            // 2: annotation is not present -> error from request model -> add a class name as first element (Final result example: UpdatePersonData.identity.firstName)

            var parts = error.getPropertyPath().toString().split("\\.");
            List<String> path = new ArrayList<>(Arrays.asList(parts));
            Class<?> errorRootClass = error.getRootBeanClass();
            if (errorRootClass.isAnnotationPresent(RestController.class) || errorRootClass.isAnnotationPresent(ControllerAdvice.class)) {
                path.remove(0);
            } else {
                path.add(0, errorRootClass.getSimpleName());
            }

            var violationAnnotationName = error.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            Violation violation;
            switch (violationAnnotationName) {
                case "NotBlank":
                case "NotNull":
                case "NotEmpty":
                    violation = new MissingFieldViolation(null, path, message);
                    break;
                case "NotEditable":
                    violation = new FieldCannotBeUpdatedViolation(null, path, message);
                    break;
                default:
                    violation = new InvalidFieldViolation(null, path, message);
            }
            problem.addViolation(violation);
        }
        return response(problem, ex);
    }

    /*
      How to produce:
      Make request with invalid JSON structure. For example, disclosed parenthesis or quotes
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemResponse> handle(HttpMessageNotReadableException ex) {
        Throwable th = ex.getCause();
        if (th == null) {
            th = ex;
        }

        if (th instanceof JsonMappingException) {
            return handle((JsonMappingException) th);
        }

        if (th instanceof JsonParseException) {
            return handle((JsonParseException) th);
        }

        var problem = new ValidationProblem(th.getMessage());
        return response(problem, ex);
    }

    /*
       How to produce:
       Make request with json body with invalid value for integer type but valid for float. For example, there is field int type (amount) and there is request with 123.45 value.
       Make request with invalid OffsetDateTime value for JSON field.
       Exception will be thrown by Jackson
     */
    @ExceptionHandler(value = JsonMappingException.class)
    public ResponseEntity<ProblemResponse> handle(JsonMappingException ex) {
        var path = ex.getPath();
        if (path.isEmpty()) {
            Problem problem = new ValidationProblem(ex.getMessage());
            return response(problem, ex);
        }

        List<String> fieldPath = path.stream().map(JsonMappingException.Reference::getFieldName).collect(Collectors.toList());
        Class<?> rootType = ex.getPath().get(0).getFrom().getClass();

        Throwable rootCause = rootCause(ex);
        String message = rootCause.getMessage();
        Problem problem = new ValidationProblem();
        Violation violation;
        // if we can't define @NotEmpty on enum field but we need to raise missing-field violation code for "" value
        // root cause of this hack is necessity to support both missing_field and invalid_field violation code
        // we can refuse to maintain missing_field code and use InvalidFieldViolation only here
        if (rootCause instanceof MissingFieldViolationException) {
            violation = new MissingFieldViolation(rootType, fieldPath, message);
        } else if (rootCause instanceof InvalidFormatException && isEmpty((InvalidFormatException) rootCause)) {
            violation = new MissingFieldViolation(rootType, fieldPath, message);
        } else {
            violation = new InvalidFieldViolation(rootType, fieldPath, message);
        }
        problem.addViolation(violation);

        return response(problem, ex);
    }

    private boolean isEmpty(InvalidFormatException rootCause) {
        return Optional.ofNullable(rootCause.getValue())
                .map(Objects::toString)
                .map(Strings::isEmpty)
                .orElse(false);
    }

    /*
        How to produce:
        Make POST request with body with totally broken JSON structure. For example ABRACADBRA instead of {}
        Exception will be thrown by Jackson
    */
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ProblemResponse> handle(JsonParseException ex) {
        Problem problem = new ValidationProblem(ex.getMessage());
        return response(problem, ex);
    }

    /*
        How to produce:
        Make POST request with JSON object with some invalid field value. For example, if there is @NotNull annotation on field, but request value is null
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemResponse> handle(MethodArgumentNotValidException ex) {

        // kind of hack here because of requirements to handle blank, null and empty values as missing_field
        // but although this is more consistent with invalid_field
        // see also JsonMappingException handler
        List<String> missingCodes = List.of("NotBlank", "NotNull", "NotEmpty");
        Problem problem = new ValidationProblem();
        Class<?> rootType = ex.getParameter().getParameterType();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String[] path = error.getField().split("\\.");
            Violation violation;
            if (missingCodes.contains(error.getCode())) {
                violation = new MissingFieldViolation(rootType, List.of(path), error.getDefaultMessage());
            } else {
                violation = new InvalidFieldViolation(rootType, List.of(path), error.getDefaultMessage());
            }
            problem.addViolation(violation);
        }
        return response(problem, ex);
    }

    /*
        How to produce:
        Make GET request with type mismatched value of some path parameter. For example, if path param is Boolean, but request value is 12345
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemResponse> handle(MethodArgumentTypeMismatchException ex) {
        String name = ex.getParameter().getParameterName();
        if (StringUtils.isBlank(name)) {
            Problem problem = new ValidationProblem(ex.getMessage());
            return response(problem, ex);
        }

        Problem problem = new ValidationProblem();
        Violation violation = new InvalidFieldViolation(null, List.of(name), ex.getMessage());
        problem.addViolation(violation);
        return response(problem, ex);
    }

    /*
        How to produce:
        Make GET request with no required request parameter. For example, if the request parameter "name" in endpoint "/streets?name=some_name" is required, then invoke  "/streets"
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ProblemResponse> handle(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        if (StringUtils.isBlank(name)) {
            Problem problem = new ValidationProblem(ex.getMessage());
            return response(problem, ex);
        }

        Problem problem = new ValidationProblem();
        Violation violation = new MissingFieldViolation(null, List.of(name), ex.getMessage());
        problem.addViolation(violation);
        return response(problem, ex);
    }

    @ExceptionHandler(value = ProblemException.class)
    public ResponseEntity<ProblemResponse> handle(ProblemException exception) {
        return response(exception.getProblem(), exception.getCause());
    }
}
