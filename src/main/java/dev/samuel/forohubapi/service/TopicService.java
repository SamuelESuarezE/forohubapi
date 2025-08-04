package dev.samuel.forohubapi.service;

import dev.samuel.forohubapi.dto.TopicCreateDTO;
import dev.samuel.forohubapi.dto.TopicDTO;
import dev.samuel.forohubapi.exceptions.DuplicatedResourceException;
import dev.samuel.forohubapi.model.Topic;
import dev.samuel.forohubapi.repository.TopicRepository;
import dev.samuel.forohubapi.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private final TopicRepository repository;
    private final UserRepository userRepository;


    public TopicService(TopicRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Page<Topic> getAllTopics(Pageable pageable) {
        return repository.findAllByOrderByDateDesc(pageable);
    }

    public Topic createTopic(TopicCreateDTO data, Long userId) {
        var sameTopicFound = repository.findAllByTitleAndMessage(data.title(), data.message());

        if (sameTopicFound.isPresent()) {
            throw new DuplicatedResourceException("Duplicated topic with the same title and message.");
        }

        var user = userRepository.getReferenceById(userId);
        var topic = new Topic(data, user);
        repository.save(topic);
        return topic;
    }

    public Topic getTopicById(Long id) {
        return repository.getReferenceById(id);
    }
}
