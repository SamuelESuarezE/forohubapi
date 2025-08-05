package dev.samuel.forohubapi.model;

import dev.samuel.forohubapi.dto.TopicCreateDTO;
import dev.samuel.forohubapi.dto.TopicUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
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

    public void update(@Valid TopicUpdateDTO data) {
        var noData = data.title() == null && data.message() == null && data.status() == null;

        if (noData) {
            throw new IllegalArgumentException("At least one field must be provided to update the topic.");
        }

        if (data.title() != null) this.title = data.title();
        if (data.message() != null) this.message = data.message();
        if (data.status() != null) this.status = data.status();
    }
}
