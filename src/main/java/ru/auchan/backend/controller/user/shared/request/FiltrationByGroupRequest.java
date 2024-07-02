package ru.auchan.backend.controller.user.shared.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

public record FiltrationByGroupRequest(
    @NotEmpty Set<@NotNull UUID> keycloakUserIds, @Nullable Set<@NotNull String> groupNames) {
  // checkstyle
}
