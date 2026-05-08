package ru.auchan.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.auchan.backend.dto.AuthRequest;
import ru.auchan.backend.dto.AuthResponse;
import ru.auchan.backend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Управление пользователями", description = "API для создания пользователя и получения токена")
public class AuthController {

    private final UserServiceImpl userService;

    @Operation(summary = "Добавить нового пользователя")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        userService.register(request.getLogin(), request.getPassword());
        return ResponseEntity.ok(AuthResponse.builder()
                .login(request.getLogin())
                .build());
    }

    @Operation(summary = "Получить токен", description = "Авторизирует пользователя и возвращает токен")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = userService.login(request.getLogin(), request.getPassword());
        return ResponseEntity.ok(AuthResponse.builder()
                .token(response.getToken())
                .login(request.getLogin())
                .build());
    }
}