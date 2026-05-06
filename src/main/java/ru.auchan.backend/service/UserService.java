package ru.auchan.backend.service;

import ru.auchan.backend.dto.AuthResponse;

public interface UserService {
    AuthResponse login(String login, String password);

    AuthResponse register(String login, String password);
}
