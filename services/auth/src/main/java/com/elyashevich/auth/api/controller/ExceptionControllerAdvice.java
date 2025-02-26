package com.elyashevich.auth.api.controller;

import com.elyashevich.auth.api.dto.ExceptionBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final String NOT_SUPPORTED_MESSAGE = "Http method with this URL not found.";
    private static final String FAILED_VALIDATION_MESSAGE = "Validation failed.";
    private static final String UNEXPECTED_ERROR_MESSAGE = "Something went wrong.";

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionBody> handleException(final HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.handleException(exception, NOT_SUPPORTED_MESSAGE));
    }

    @SuppressWarnings("all")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> handleValidation(final MethodArgumentNotValidException exception) {
        var errors = exception.getBindingResult()
                .getFieldErrors().stream()
                .collect(Collectors.toMap(
                                FieldError::getField,
                                fieldError -> fieldError.getDefaultMessage(),
                                (exist, newMessage) -> exist + " " + newMessage
                        )
                );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionBody(FAILED_VALIDATION_MESSAGE, errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBody> handleException(final Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.handleException(exception, UNEXPECTED_ERROR_MESSAGE));
    }

    private ExceptionBody handleException(final Exception exception, final String defaultMessage) {
        var message = exception.getMessage() == null ? defaultMessage : exception.getMessage();
        log.warn("{} '{}'.", defaultMessage, message);
        return new ExceptionBody(message);
    }
}