package ru.auchan.backend.dto;

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
    @NotBlank(message = "Username is required")
    private String login;

    @NotBlank(message = "Password is required")
    private String password;

    private AuthRequest authRequest;
    private User user;
}
