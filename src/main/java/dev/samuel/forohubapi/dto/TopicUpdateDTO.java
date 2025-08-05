package dev.samuel.forohubapi.dto;

import dev.samuel.forohubapi.model.TopicStatus;

public record TopicUpdateDTO(
        String title,
        String message,
        TopicStatus status
) {
}
