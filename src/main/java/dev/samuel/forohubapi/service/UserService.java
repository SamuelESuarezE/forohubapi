package dev.samuel.forohubapi.service;

import dev.samuel.forohubapi.dto.AuthDTO;
import dev.samuel.forohubapi.exceptions.DuplicatedResourceException;
import dev.samuel.forohubapi.model.User;
import dev.samuel.forohubapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.repository = userRepository;
        this.encoder = encoder;
    }

    public User signup(AuthDTO authDTO) {
        if (repository.existsByEmail(authDTO.email())) throw new DuplicatedResourceException("User with email " + authDTO.email() + " already exists.");

        var encodedPassword = encoder.encode(authDTO.password());
        var user = new User(authDTO.email(), encodedPassword);
        repository.save(user);

        return user;
    }
}
