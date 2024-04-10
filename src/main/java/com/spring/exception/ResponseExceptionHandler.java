package com.spring.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    // Desarrollar excepciones custom
    // Forma: se puede ocupar desde Spring Boot 1.5 hasta el día de hoy
    public ResponseEntity<CustomErrorResponse> handleModelNotFoundException(ModelNotFoundException ex,
        WebRequest request) {
       CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(),
            request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    // Pedientes definiciones 2 y 3 para las personalizadas

    // para sobreescribir definiciones de la clase padre
    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // Devolver una lista de errores para transformar cada elemento de la lista capturando sus errores generados
        String msg = ex.getBindingResult().getFieldErrors().stream().map(
            e -> e.getField().concat(":").concat(e.getDefaultMessage().concat(" "))
        ).collect(Collectors.joining()); // permite agrupar todas las iteraciones

        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg, request.getDescription(false));
      
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

}
