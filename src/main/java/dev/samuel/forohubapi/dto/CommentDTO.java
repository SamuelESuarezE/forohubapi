package dev.samuel.forohubapi.dto;

import dev.samuel.forohubapi.model.Comment;

public record CommentDTO(
        Long user_id,
        String message
) {
    public CommentDTO(Comment comment) {
        this(comment.getUser().getId(), comment.getMessage());
    }
}
