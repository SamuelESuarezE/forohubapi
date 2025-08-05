package dev.samuel.forohubapi.controller;

import dev.samuel.forohubapi.dto.CommentCreateDTO;
import dev.samuel.forohubapi.dto.CommentDTO;
import dev.samuel.forohubapi.dto.TopicDTO;
import dev.samuel.forohubapi.service.CommentService;
import dev.samuel.forohubapi.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService service;
    private TokenService tokenService;

    public CommentController(CommentService service, TokenService tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody @Valid CommentCreateDTO data, HttpServletRequest req, UriComponentsBuilder uriComponentsBuilder) {
        var userId = tokenService.getUserIdFromRequest(req);
        var comment = service.createComment(data, userId);

        var uri = uriComponentsBuilder.path("/comment/{id}").buildAndExpand(comment.getId()).toUri();

        return ResponseEntity.created(uri).body(new CommentDTO(comment));
    }
}
