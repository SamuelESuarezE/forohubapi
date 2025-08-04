package dev.samuel.forohubapi.model;

import dev.samuel.forohubapi.dto.TopicCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TopicStatus status;

    @ManyToOne
    private User user;

    public Topic(TopicCreateDTO data, User user) {
        this.title = data.title();
        this.message = data.message();
        this.date = LocalDate.now();
        this.status = TopicStatus.OPEN;
        this.user = user;
    }
}
