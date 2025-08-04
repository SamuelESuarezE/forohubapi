package dev.samuel.forohubapi.service;

import dev.samuel.forohubapi.dto.CommentCreateDTO;
import dev.samuel.forohubapi.model.Comment;
import dev.samuel.forohubapi.repository.CommentRepository;
import dev.samuel.forohubapi.repository.TopicRepository;
import dev.samuel.forohubapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository repository, TopicRepository topicRepository, UserRepository userRepository) {
        this.repository = repository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public Comment createComment(CommentCreateDTO data, Long userId) {
        var user = userRepository.getReferenceById(userId);
        var topic = topicRepository.findById(data.topic_id()).orElseThrow(() -> new EntityNotFoundException("Topic with ID " + data.topic_id() + " not found"));

        var comment = new Comment(data, user, topic);
        repository.save(comment);
        return comment;
    }

    public List<Comment> getCommentsByTopicId(Long topicId) {
        return repository.findAllByTopic_Id(topicId);
    }
}
