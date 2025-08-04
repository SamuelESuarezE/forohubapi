package dev.samuel.forohubapi.exceptions;

import java.sql.Timestamp;

public record ErrorResponse (
        Timestamp time,
        Integer code,
        String message
) {
}
