package ru.auchan.backend.service.impl;

import lombok.RequiredArgsConstructor;
import ru.auchan.backend.dto.UserMapper;
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
import ru.auchan.backend.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final JwtUtil jwtUtil;

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

    public AuthResponse register(String login, String password) {
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

        User res = userRepository.save(user);
        return userMapper.toResponse(res);
    }
}