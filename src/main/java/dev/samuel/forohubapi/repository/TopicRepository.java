package dev.samuel.forohubapi.repository;

import dev.samuel.forohubapi.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findAllByOrderByDateDesc(Pageable pageable);

    Optional<Topic> findAllByTitleAndMessage(String title, String message);
}
