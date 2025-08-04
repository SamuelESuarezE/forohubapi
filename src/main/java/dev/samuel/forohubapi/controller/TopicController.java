package dev.samuel.forohubapi.controller;

import dev.samuel.forohubapi.dto.TopicDTO;
import dev.samuel.forohubapi.dto.TopicWithCommentsDTO;
import dev.samuel.forohubapi.service.CommentService;
import dev.samuel.forohubapi.service.TokenService;
import dev.samuel.forohubapi.dto.TopicCreateDTO;
import dev.samuel.forohubapi.service.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private final TopicService service;
    private final CommentService commentService;
    private final TokenService tokenService;

    public TopicController(TopicService service, CommentService commentService, TokenService tokenService) {
        this.service = service;
        this.commentService = commentService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public Page<TopicDTO> getAllTopics(@PageableDefault(size = 10) Pageable pageable, Sort sort) {
        return service.getAllTopics(pageable).map(TopicDTO::new);
    }

    @GetMapping("/{id}")
    public TopicWithCommentsDTO getTopicById(@PathVariable Long id) {
        var topic = service.getTopicById(id);
        var comments = commentService.getCommentsByTopicId(id);
        return new TopicWithCommentsDTO(topic, comments);
    }

    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody @Valid TopicCreateDTO data, HttpServletRequest req, UriComponentsBuilder uriComponentsBuilder) {
        var userId = tokenService.getUserIdFromRequest(req);
        var topic = service.createTopic(data, userId);

        var uri = uriComponentsBuilder.path("/topic/{id}").buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicDTO(topic));
    }
}
