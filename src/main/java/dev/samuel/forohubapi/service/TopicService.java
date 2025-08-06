package dev.samuel.forohubapi.service;

import dev.samuel.forohubapi.dto.TopicCreateDTO;
import dev.samuel.forohubapi.dto.TopicDTO;
import dev.samuel.forohubapi.dto.TopicUpdateDTO;
import dev.samuel.forohubapi.exceptions.DuplicatedResourceException;
import dev.samuel.forohubapi.exceptions.ForbiddenActionException;
import dev.samuel.forohubapi.model.Comment;
import dev.samuel.forohubapi.model.Topic;
import dev.samuel.forohubapi.repository.CommentRepository;
import dev.samuel.forohubapi.repository.TopicRepository;
import dev.samuel.forohubapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private final TopicRepository repository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public TopicService(TopicRepository repository, UserRepository userRepository, CommentRepository commentRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public Page<Topic> getAllTopics(Pageable pageable) {
        return repository.findAllByOrderByDateDesc(pageable);
    }

    public Topic createTopic(TopicCreateDTO data, Long userId) {
        validateDuplicatedTopic(data.title(), data.message());

        var user = userRepository.getReferenceById(userId);
        var topic = new Topic(data, user);
        repository.save(topic);
        return topic;
    }

    public Topic getTopicById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic with ID " + id + " not found"));
    }

    public Topic updateTopic(Long id, TopicUpdateDTO data, Long userId) {
        var topic = getTopicById(id);
        validateUserIsAuthorOfTopic(userId, topic);
        validateDuplicatedTopic(data.title(), data.message());
        topic.update(data);
        return topic;
    }

    public Topic deleteTopic(Long id, Long userId) {
        var topic = getTopicById(id);
        validateUserIsAuthorOfTopic(userId, topic);

        var comments = commentRepository.findAllByTopic_Id(topic.getId());

        for (Comment comment : comments) {
            commentRepository.deleteById(comment.getId());
        }

        repository.deleteById(topic.getId());

        return topic;
    }

    private void validateDuplicatedTopic(String title, String message) {
        if (title == null || message == null) return;

        var sameTopicFound = repository.findAllByTitleAndMessage(title, message).isPresent();

        if (sameTopicFound) {
            throw new DuplicatedResourceException("Duplicated topic with the same title and message.");
        }
    }

    private void validateUserIsAuthorOfTopic(Long userId, Topic topic) {
        if (!userId.equals(topic.getUser().getId())) throw new ForbiddenActionException("You don't have permission to update this topic.");
    }
}
