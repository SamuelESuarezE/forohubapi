package dev.samuel.forohubapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateDTO(
        @NotBlank String message,
        @NotNull Long topic_id
) {
}
