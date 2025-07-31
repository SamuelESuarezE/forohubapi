package dev.samuel.forohubapi.model;

import java.time.LocalDate;

public class Topic {
    private Long id;
    private String title;
    private String message;
    private LocalDate date;
    private TopicStatus status;
    private User author;
}
