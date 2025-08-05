package dev.samuel.forohubapi.controller;

import dev.samuel.forohubapi.dto.TopicDTO;
import dev.samuel.forohubapi.dto.TopicUpdateDTO;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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

    @Transactional
    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody @Valid TopicCreateDTO data, HttpServletRequest req, UriComponentsBuilder uriComponentsBuilder) {
        var userId = tokenService.getUserIdFromRequest(req);
        var topic = service.createTopic(data, userId);

        return ResponseEntity.created(uriBuildFromId(topic.getId(), uriComponentsBuilder)).body(new TopicDTO(topic));
    }

    @Transactional
    @PutMapping("/{id}")
    public TopicDTO updateTopic(@PathVariable Long id, @RequestBody @Valid TopicUpdateDTO data, HttpServletRequest req) {
        var topic = service.updateTopic(id, data, tokenService.getUserIdFromRequest(req));

        return new TopicDTO(topic);
    }

    private URI uriBuildFromId(Long id, UriComponentsBuilder uriComponentsBuilder) {
        return uriComponentsBuilder.path("/topic/{id}").buildAndExpand(id).toUri();
    }
}
