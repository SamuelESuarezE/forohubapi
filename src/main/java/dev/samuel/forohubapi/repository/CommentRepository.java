package dev.samuel.forohubapi.repository;

import dev.samuel.forohubapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTopic_Id(Long topicId);
}
