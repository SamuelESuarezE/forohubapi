package dev.samuel.forohubapi.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicCreateDTO (
        @NotBlank String title,
        @NotBlank String message
){}
