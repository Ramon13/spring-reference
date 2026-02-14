package com.javamoon.spring_algaworks.api.exceptionhandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.javamoon.spring_algaworks.api.exceptionhandler.Problem.Field;
import com.javamoon.spring_algaworks.domain.exception.BusinessException;
import com.javamoon.spring_algaworks.domain.exception.EntityInUseException;
import com.javamoon.spring_algaworks.domain.exception.EntityNotFoundExeption;

import tools.jackson.core.JacksonException.Reference;
import tools.jackson.databind.exc.InvalidFormatException;
import tools.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
    
    private static final String GENERIC_USER_MESSAGE = "An unexpected error occurred. Please try again." 
     + "If the problem persists, contact the system administrator";

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        List<Field> problemFields = bindingResult.getFieldErrors().stream()
            .map(field -> Problem.Field.builder()
                .name(field.getField())
                .userMessage(field.getDefaultMessage())
                .build()
            )
            .toList();

        String detail = "One or more fields are invalid. Please correct them and try again.";
        Problem problem = createProblemBuilder(BAD_REQUEST, ProblemType.INVALID_DATA, detail)
            .userMessage(detail)
            .fields(problemFields)
            .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detail = String.format("The resource '%s' doesn't exists", ex.getResourcePath());
        Problem problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, detail)
            .userMessage(GENERIC_USER_MESSAGE)
            .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException argumentTypeEx) {
            return handleMethodArgumentTypeMismatch(argumentTypeEx, headers, status, request);
        }
        
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        ex.printStackTrace();

        if (rootCause instanceof InvalidFormatException invalidFormatEx) {
            return handleInvalidFormat(invalidFormatEx, headers, status, request);
        }

        if (rootCause instanceof PropertyBindingException propertyBindingEx) {
            return handlePropertyBindingException(propertyBindingEx, headers, status, request);
        }
        
        String detail = "The request body is unreadable. Please check for syntax errors";    
        Problem problem = createProblemBuilder(status, ProblemType.UNREADABLE_MESSAGE, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
        String detail = "An unexpected error occurred. Please try again. If the problem persists, contact the system administrator";
        Problem problem = createProblemBuilder(INTERNAL_SERVER_ERROR, ProblemType.SYSTEM_ERROR, detail).build();
        
        ex.printStackTrace();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), INTERNAL_SERVER_ERROR, request); 
    }

    @ExceptionHandler(EntityNotFoundExeption.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundExeption e, WebRequest request) {
        HttpStatusCode status = NOT_FOUND;

        Problem problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, e.getMessage()).build();
            
        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e, WebRequest request) {
        Problem problem = createProblemBuilder(BAD_REQUEST, ProblemType.BUSINESS_EXCEPTION, e.getMessage()).build();
        
        return handleExceptionInternal(e, problem, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException e, WebRequest request) {
        Problem problem = createProblemBuilder(CONFLICT, ProblemType.ENTITY_IN_USE, e.getMessage()).build();        

        return handleExceptionInternal(e, problem, new HttpHeaders(), CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
        HttpStatusCode statusCode, WebRequest request) {
        
        if (body == null) {
            body = Problem.builder()
                .status(statusCode.value())
                .title(statusCode.toString())
                .userMessage(GENERIC_USER_MESSAGE)
                .build();
        } else if (body instanceof String bodyStr) {
            body = Problem.builder()
                .status(statusCode.value())
                .title(bodyStr)
                .userMessage(GENERIC_USER_MESSAGE)
                .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request) {

        String detail = String.format(
            "The URL parameter '%s' receives the value '%s', which is a invalid type. Please provide a value compatible with the type %s",
            ex.getPropertyName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        Problem problem = createProblemBuilder(status, ProblemType.INVALID_PARAM, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request) {
         
        String errorPath = joinPath(ex.getPath());

        String detail = String.format(
            "The property '%s' receives the value '%s', which is a invalid type. Please provide a value compatible with %s", 
            errorPath, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, ProblemType.UNREADABLE_MESSAGE, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status, ProblemType problemType, String detail) {
        return Problem.builder()
            .status(status.value())
            .title(problemType.getTitle())
            .type(problemType.getUri())
            .timestamp(LocalDateTime.now())
            .detail(detail);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request) {

        String errorPath = joinPath(ex.getPath());

        String detail = String.format("Invalid property: '%s'. Please remove it and try again", errorPath);
        Problem problem = createProblemBuilder(status, ProblemType.UNREADABLE_MESSAGE, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
            .map(path -> path.getPropertyName())
            .collect(Collectors.joining("."));
    }
}
