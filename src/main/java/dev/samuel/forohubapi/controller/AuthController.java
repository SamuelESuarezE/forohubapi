package dev.samuel.forohubapi.controller;

import dev.samuel.forohubapi.service.TokenService;
import dev.samuel.forohubapi.dto.LoginDTO;
import dev.samuel.forohubapi.dto.TokenJWTDTO;
import dev.samuel.forohubapi.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    private AuthenticationManager manager;
    private TokenService tokenService;

    public AuthController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenJWTDTO> login(@RequestBody @Valid LoginDTO authDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());
        var auth = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
    }
}
