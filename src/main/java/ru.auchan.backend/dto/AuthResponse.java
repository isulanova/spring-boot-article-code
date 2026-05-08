package ru.auchan.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    @Schema(description = "Токен авторизации")
    private String token;

    @Schema(description = "Логин пользователя", example = "login")
    private String login;

}
