package dev.samuel.forohubapi.model;

import dev.samuel.forohubapi.dto.CommentCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    @ManyToOne
    private Topic topic;

    @ManyToOne
    private User user;

    public Comment(CommentCreateDTO data, User user, Topic topic) {
        this.message = data.message();
        this.user = user;
        this.topic = topic;
    }
}
