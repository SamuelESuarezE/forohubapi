package dev.samuel.forohubapi.dto;

import dev.samuel.forohubapi.model.Topic;
import dev.samuel.forohubapi.model.TopicStatus;

import java.time.LocalDate;

public record TopicDTO(
        Long id,
        Long userId,
        String title,
        String message,
        LocalDate date,
        TopicStatus status
) {

    public TopicDTO(Topic topic) {
        this(topic.getId(), topic.getUser().getId(), topic.getTitle(), topic.getMessage(), topic.getDate(), topic.getStatus());
    }
}
