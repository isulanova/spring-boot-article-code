package ru.auchan.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import ru.auchan.backend.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @Schema(description = "Логин пользователя", example = "login")
    @NotBlank(message = "Username is required")
    private String login;

    @Schema(description = "Пароль", example = "password")
    @NotBlank(message = "Password is required")
    private String password;
}
