package dev.samuel.forohubapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthDTO (
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
