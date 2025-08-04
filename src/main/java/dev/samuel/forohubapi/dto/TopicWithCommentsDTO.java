package dev.samuel.forohubapi.dto;

import dev.samuel.forohubapi.model.Comment;
import dev.samuel.forohubapi.model.Topic;
import dev.samuel.forohubapi.model.TopicStatus;

import java.time.LocalDate;
import java.util.List;

public record TopicWithCommentsDTO(
        Long id,
        Long user_id,
        String title,
        String message,
        LocalDate date,
        TopicStatus status,
        List<CommentDTO> comments
) {
    public TopicWithCommentsDTO(Topic topic, List<Comment> comments) {
        this(topic.getId(), topic.getUser().getId(), topic.getTitle(), topic.getMessage(), topic.getDate(), topic.getStatus(), comments.stream().map(CommentDTO::new).toList());
    }
}
