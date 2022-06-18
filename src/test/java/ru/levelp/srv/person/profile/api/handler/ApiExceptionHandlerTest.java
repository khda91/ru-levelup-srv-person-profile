package ru.levelp.srv.person.profile.api.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.levelp.srv.person.profile.api.data.ProblemResponse;
import ru.levelp.srv.person.profile.api.data.ViolationData;
import ru.levelp.srv.person.profile.api.data.problem.Problem;
import ru.levelp.srv.person.profile.api.exception.MissingFieldViolationException;
import ru.levelp.srv.person.profile.api.exception.ProblemException;
import ru.levelp.srv.person.profile.infrastructure.annotation.UnitTest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.io.Closeable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@UnitTest
@DisplayName("ApiExceptionHandler Test")
class ApiExceptionHandlerTest {

    private ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    @DisplayName("should handle 5xx errors")
    void handleException() {
        Exception ex = new Exception("Message");
        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        assertThat(responseBody.getDetail()).isEqualTo(ex.getMessage());
        assertThat(responseBody.getViolations()).isNull();
    }

    @Test
    @DisplayName("should handle 405 exception")
    void handleNoHandlerFoundException() {
        NoHandlerFoundException ex = new NoHandlerFoundException("METHOD", "https://something.org", new HttpHeaders());
        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.NOT_FOUND.value()));
        assertThat(responseBody.getDetail()).isEqualTo("No handler found for METHOD https://something.org");
        assertThat(responseBody.getViolations()).isNull();
    }

    @Test
    @DisplayName("should handle 404 exception")
    void handleHttpRequestMethodNotSupportedException() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("METHOD", new String[]{"METH1", "METH2"}, "Message");
        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.NOT_FOUND.value()));
        assertThat(responseBody.getDetail()).isEqualTo(ex.getMessage());
        assertThat(responseBody.getViolations()).isNull();
    }

    @Test
    @DisplayName("should handle 400 exception with multiples violations")
    void handleConstraintViolationException() {
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("identity.firstName");

        ConstraintViolation<Object> cv1 = mock(ConstraintViolation.class);
        when(cv1.getMessage()).thenReturn("violation1");
        when(cv1.getPropertyPath()).thenReturn(path);
        when(cv1.getRootBeanClass()).thenReturn(Object.class);

        ConstraintDescriptor constraintDescriptor1 = mock(ConstraintDescriptor.class);
        when(constraintDescriptor1.getAnnotation()).thenReturn(new General());
        when(cv1.getConstraintDescriptor()).thenReturn(constraintDescriptor1);

        ConstraintViolation<RestControllerRootClass> cv2 = mock(ConstraintViolation.class);
        when(cv2.getMessage()).thenReturn("violation2");
        when(cv2.getPropertyPath()).thenReturn(path);
        when(cv2.getRootBeanClass()).thenReturn(RestControllerRootClass.class);

        ConstraintDescriptor constraintDescriptor2 = mock(ConstraintDescriptor.class);
        when(constraintDescriptor2.getAnnotation()).thenReturn(new NotEmpty());
        when(cv2.getConstraintDescriptor()).thenReturn(constraintDescriptor2);

        ConstraintViolation<ControllerRootClass> cv3 = mock(ConstraintViolation.class);
        when(cv3.getMessage()).thenReturn("violation3");
        when(cv3.getPropertyPath()).thenReturn(path);
        when(cv3.getRootBeanClass()).thenReturn(ControllerRootClass.class);

        ConstraintDescriptor constraintDescriptor3 = mock(ConstraintDescriptor.class);
        when(constraintDescriptor3.getAnnotation()).thenReturn(new NotEditable());
        when(cv3.getConstraintDescriptor()).thenReturn(constraintDescriptor3);

        ConstraintViolationException ex = new ConstraintViolationException("Message", Set.of(cv1, cv2, cv3));
        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("A malformed request was sent");

        List<ViolationData> violations = responseBody.getViolations();
        assertThat(violations).isNotNull();
        assertThat(violations).hasSize(3);

        ViolationData violationData1 = violations.stream().filter(v -> "violation1".equals(v.getDetail())).findAny().orElse(null);
        assertThat(violationData1).isNotNull();
        assertThat(violationData1.getCode()).isEqualTo("invalid_field");
        assertThat(violationData1.getField()).isEqualTo("Object.identity.firstName");

        ViolationData violationData2 = violations.stream().filter(v -> "violation2".equals(v.getDetail())).findAny().orElse(null);
        assertThat(violationData2).isNotNull();
        assertThat(violationData2.getCode()).isEqualTo("missing_field");
        assertThat(violationData2.getField()).isEqualTo("firstName");

        ViolationData violationData3 = violations.stream().filter(v -> "violation3".equals(v.getDetail())).findAny().orElse(null);
        assertThat(violationData3).isNotNull();
        assertThat(violationData3.getCode()).isEqualTo("field_cannot_be_updated");
        assertThat(violationData3.getField()).isEqualTo("ControllerRootClass.identity.firstName");
    }

    @Test
    @DisplayName("should handle 400 exception without violations")
    void handleHttpMessageValidationProblem() {
        HttpInputMessage httpInputMessage = mock(HttpInputMessage.class);
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Message", httpInputMessage);
        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("Message");
        assertThat(responseBody.getViolations()).isNull();
    }

    @Test
    @DisplayName("should handle 400 exception with JsonMappingException")
    void handleHttpMessageJsonMappingException() {
        JsonMappingException jsonMappingException = new JsonMappingException(mock(Closeable.class), "Message");
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Message", jsonMappingException, mock(HttpInputMessage.class));

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("Message");
        assertThat(responseBody.getViolations()).isNull();
    }

    @Test
    @DisplayName("should handle 400 exception with JsonParseException")
    void handleHttpMessageJsonParseException() {
        JsonParseException jsonParseException = new JsonParseException(mock(JsonParser.class), "Message");
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Message", jsonParseException, mock(HttpInputMessage.class));

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("Message");
        assertThat(responseBody.getViolations()).isNull();
    }

    @Test
    @DisplayName("should handle 400 exception with JsonMappingExceptionHasEmptyPath")
    void handleJsonMappingExceptionHasEmptyPath() {
        JsonMappingException ex = new JsonMappingException(mock(Closeable.class), "Message");

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("Message");
        assertThat(responseBody.getViolations()).isNull();
    }

    @Test
    @DisplayName("should handle 400 exception with InvalidFieldViolation")
    void handleJsonMappingExceptionInvalidFieldViolation() {
        JsonMappingException ex = new JsonMappingException(mock(Closeable.class), "Message");
        ex.prependPath(new General(), "type");

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("A malformed request was sent");

        List<ViolationData> violations = responseBody.getViolations();
        assertThat(violations).isNotNull();

        ViolationData violationData = violations.get(0);
        assertThat(violationData.getCode()).isEqualTo("invalid_field");
        assertThat(violationData.getField()).isEqualTo("General.type");
    }

    @Test
    @DisplayName("should handle 400 exception with MissingFieldViolation")
    void handleJsonMappingMissingFieldViolationException() {
        MissingFieldViolationException rootCause = new MissingFieldViolationException(Collections.emptyList(), "Message");

        JsonMappingException ex = new JsonMappingException(mock(Closeable.class), "Message", rootCause);
        ex.prependPath(new General(), "type");

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("A malformed request was sent");

        List<ViolationData> violations = responseBody.getViolations();
        assertThat(violations).isNotNull();

        ViolationData violationData = violations.get(0);
        assertThat(violationData.getCode()).isEqualTo("missing_field");
        assertThat(violationData.getField()).isEqualTo("General.type");
    }

    @Test
    @DisplayName("should handle 400 exception with InvalidFormatException")
    void handleJsonMappingInvalidFormatException() {
        InvalidFormatException rootCause = new InvalidFormatException(mock(JsonParser.class), "Message", "String", String.class);
        JsonMappingException ex = new JsonMappingException(mock(Closeable.class), "Message", rootCause);
        ex.prependPath(new General(), "type");

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("A malformed request was sent");

        List<ViolationData> violations = responseBody.getViolations();
        assertThat(violations).isNotNull();

        ViolationData violationData = violations.get(0);
        assertThat(violationData.getCode()).isEqualTo("invalid_field");
        assertThat(violationData.getField()).isEqualTo("General.type");
    }

    @Test
    @DisplayName("should handle 400 exception with InvalidFormatExceptionWithEmptyRootCause")
    void handleJsonMappingInvalidFormatExceptionWithEmptyRootCause() {
        InvalidFormatException rootCause = new InvalidFormatException(mock(JsonParser.class), "Message", "", String.class);
        JsonMappingException ex = new JsonMappingException(mock(Closeable.class), "Message", rootCause);
        ex.prependPath(new General(), "type");

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("A malformed request was sent");

        List<ViolationData> violations = responseBody.getViolations();
        assertThat(violations).isNotNull();

        ViolationData violationData = violations.get(0);
        assertThat(violationData.getCode()).isEqualTo("missing_field");
        assertThat(violationData.getField()).isEqualTo("General.type");
    }

    @Test
    @DisplayName("should handle 400 exception with MethodArgumentNotValidException if no field errors")
    void handleMethodArgumentNotValidExceptionIfNoFieldErrors() {
        Executable executable = mock(Executable.class);
        when(executable.toGenericString()).thenReturn("Generic_String");

        MethodParameter methodParameter = mock(MethodParameter.class);
        when(methodParameter.getParameterIndex()).thenReturn(0);
        when(methodParameter.getExecutable()).thenReturn(executable);

        BindingResult bindingResult = mock(BindingResult.class);

        MethodArgumentNotValidException ex = spy(new MethodArgumentNotValidException(methodParameter, bindingResult));
        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("A malformed request was sent");
        assertThat(responseBody.getViolations()).isNull();
    }

    @Test
    @DisplayName("should handle 400 exception with MethodArgumentNotValidException")
    void handleMethodArgumentNotValidException() {
        Executable executable = mock(Executable.class);
        when(executable.toGenericString()).thenReturn("Generic_String");

        MethodParameter methodParameter = mock(MethodParameter.class);
        when(methodParameter.getParameterIndex()).thenReturn(0);
        when(methodParameter.getExecutable()).thenReturn(executable);

        FieldError fe1 = mock(FieldError.class);
        when(fe1.getField()).thenReturn("Object.firstName");
        when(fe1.getCode()).thenReturn("General");

        FieldError fe2 = mock(FieldError.class);
        when(fe2.getField()).thenReturn("Object.lastName");
        when(fe2.getCode()).thenReturn("NotNull");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fe1, fe2));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);
        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getDetail()).isEqualTo("A malformed request was sent");

        List<ViolationData> violations = responseBody.getViolations();
        assertThat(violations).hasSize(2);

        ViolationData violationData1 = violations.get(0);
        assertThat(violationData1.getCode()).isEqualTo("invalid_field");
        assertThat(violationData1.getField()).isEqualTo("Object.firstName");
        ViolationData violationData2 = violations.get(1);
        assertThat(violationData2.getCode()).isEqualTo("missing_field");
        assertThat(violationData2.getField()).isEqualTo("Object.lastName");
    }

    @Test
    @DisplayName("should handle 400 exception with MethodArgumentTypeMismatchException has blank parameter")
    void handleMethodArgumentTypeMismatchExceptionHasBlankParameterName() {
        MethodParameter methodParameter = mock(MethodParameter.class);
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException("String value", null, "", methodParameter, new IllegalArgumentException());

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getViolations()).isNull();
    }

    @Test
    @DisplayName("should handle 400 exception with MethodArgumentTypeMismatchException")
    void handleMethodArgumentTypeMismatchException() {
        MethodParameter methodParameter = mock(MethodParameter.class);
        when(methodParameter.getParameterName()).thenReturn("testName");
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException("String value", null, "", methodParameter, new IllegalArgumentException());

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));

        List<ViolationData> violations = responseBody.getViolations();
        assertThat(violations).hasSize(1);

        ViolationData violationData = violations.get(0);
        assertThat(violationData.getCode()).isEqualTo("invalid_field");
        assertThat(violationData.getField()).isEqualTo("testName");
    }

    @Test
    @DisplayName("should handle 400 exception with MissingServletRequestParameterException has blank parameter")
    void handleMissingServletRequestParameterExceptionHasBlankParameterName() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException(null, "ParameterType");

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getViolations()).isNull();
    }

    @Test
    @DisplayName("should handle 400 exception with MissingServletRequestParameterException")
    void handleMissingServletRequestParameterException() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("testName", "testType");

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));

        List<ViolationData> violations = responseBody.getViolations();
        assertThat(violations).hasSize(1);

        ViolationData violationData = violations.get(0);
        assertThat(violationData.getCode()).isEqualTo("missing_field");
        assertThat(violationData.getField()).isEqualTo("testName");
    }

    @Test
    @DisplayName("should handle 400 exception with handleProblemException")
    void handleProblemException() {
        Problem problem = mock(Problem.class);
        when(problem.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);
        when(problem.getTitle()).thenReturn(HttpStatus.BAD_REQUEST.getReasonPhrase());

        ProblemException ex = mock(ProblemException.class);
        when(ex.getProblem()).thenReturn(problem);

        ResponseEntity<ProblemResponse> response = handler.handle(ex);

        ProblemResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        assertThat(responseBody.getStatus()).isEqualTo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertThat(responseBody.getViolations()).isNull();
    }

    private static class General implements Annotation {
        @Override
        public Class<? extends Annotation> annotationType() {
            return this.getClass();
        }
    }

    private static final class NotEmpty extends General {
    }

    private static final class NotEditable extends General {
    }

    @RestController
    private static final class RestControllerRootClass {
    }

    @Controller
    private static final class ControllerRootClass {
    }
}
