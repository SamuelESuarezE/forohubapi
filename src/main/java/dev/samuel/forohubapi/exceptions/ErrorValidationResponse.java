package dev.samuel.forohubapi.exceptions;

import java.sql.Timestamp;
import java.util.List;

public record ErrorValidationResponse (
        Timestamp time,
        Integer code,
        String message,
        List<ValidationError> validation_errors
) {
}
