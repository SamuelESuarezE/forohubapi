package dev.samuel.forohubapi.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.util.List;

@RestControllerAdvice
public class ErrorManager {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleNotFound() {
        return new ErrorResponse(
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.NOT_FOUND.value(),
                "Entity not found"
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorValidationResponse handleBadRequest(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors().stream().map(ValidationError::new).toList();
        return new ErrorValidationResponse(
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST.value(),
                errors.size() + " field(s) failed validation",
                errors
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicatedResourceException.class)
    public ErrorResponse handleDuplicatedResource(DuplicatedResourceException exception) {
        return new ErrorResponse(
                new Timestamp(System.currentTimeMillis()),
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );
    }


}
