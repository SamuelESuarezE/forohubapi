package dev.samuel.forohubapi.controller;

import dev.samuel.forohubapi.dto.AuthDTO;
import dev.samuel.forohubapi.service.TokenService;
import dev.samuel.forohubapi.dto.TokenJWTDTO;
import dev.samuel.forohubapi.model.User;
import dev.samuel.forohubapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthController(AuthenticationManager manager, TokenService tokenService, UserService userService) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenJWTDTO> login(@RequestBody @Valid AuthDTO authDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.password());
        var auth = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
    }

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<TokenJWTDTO> signup(@RequestBody @Valid AuthDTO authDTO) {
        userService.signup(authDTO);
        return login(authDTO);
    }
}
