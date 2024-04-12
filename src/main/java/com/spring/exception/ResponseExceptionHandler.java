package com.spring.exception;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.micrometer.common.lang.Nullable;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleAllException(Exception ex, WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Desarrollar excepciones custom: forma 1 convencional
    // Forma: se puede ocupar desde Spring Boot 1.5 hasta el día de hoy

    public ResponseEntity<CustomErrorResponse> handleModelNotFoundException(ModelNotFoundException ex,
            WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    // Forma 2 de personalización: Desde Spring Boot 3

    /*
     * @ExceptionHandler(ModelNotFoundException.class)
     * public ProblemDetail handleModelNotFoundException(ModelNotFoundException ex,
     * WebRequest request) {
     * ProblemDetail problemDetail =
     * ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
     * // personalización
     * problemDetail.setTitle("Model Not Found Exception");
     * problemDetail.setType(URI.create("/not-found")); // bloque de info.
     * problemDetail.setProperty("extra-1", "error-type-1");
     * problemDetail.setProperty("extra-2", 32);
     * return problemDetail;
     * }
     */

    // Forma 3 de personalización: Desde Spring boot 3
    /*
     * @ExceptionHandler(ModelNotFoundException.class)
     * public ErrorResponse handleModelNotFoundException(ModelNotFoundException ex,
     * WebRequest request) {
     * return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage())
     * .title("Model Not Found")
     * .type(URI.create(request.getContextPath()))
     * .property("extra-1", "error-type-1")
     * .property("extra-2", 32)
     * .build();
     * }
     */

    // para sobreescribir definiciones de la clase padre
    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // Devolver una lista de errores para transformar cada elemento de la lista
        // capturando sus errores generados
        String msg = ex.getBindingResult().getFieldErrors().stream().map(
                e -> e.getField().concat(":").concat(e.getDefaultMessage().concat(" "))).collect(Collectors.joining());
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg, request.getDescription(false));

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

}
