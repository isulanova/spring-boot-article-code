package ru.auchan.backend.controller;

import ru.auchan.backend.dto.AuthRequest;
import ru.auchan.backend.dto.AuthResponse;
import ru.auchan.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        userService.register(request.getLogin(), request.getPassword());
        return ResponseEntity.ok(AuthResponse.builder()
                .login(request.getLogin())
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = userService.login(request.getLogin(), request.getPassword());
        return ResponseEntity.ok(AuthResponse.builder()
                .token(response.getToken())
                .login(request.getLogin())
                .build());
    }
}