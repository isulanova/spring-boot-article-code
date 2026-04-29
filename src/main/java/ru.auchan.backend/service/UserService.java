package ru.auchan.backend.service;

import ru.auchan.backend.dto.AuthRequest;
import ru.auchan.backend.dto.AuthResponse;
import ru.auchan.backend.exception.ResourceNotFoundException;
import ru.auchan.backend.exception.ResourceAlreadyExistsException;
import ru.auchan.backend.exception.InvalidCredentialsException;
import ru.auchan.backend.entity.User;
import ru.auchan.backend.repository.UserRepository;
import ru.auchan.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(String login, String password) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User",
                        "login",
                        login
                ));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        AuthResponse response = new AuthResponse();
        response.setLogin(login);
        response.setToken(jwtUtil.generateToken(login));
        return response;
    }

    public User register(String login, String password) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "User",
                    "login",
                    login
            );
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
        return user;
    }
}