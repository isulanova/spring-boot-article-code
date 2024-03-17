package ru.auchan.backend.controller.shared.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "Auth user creation request")
public class UserCreateRequest {

  @Schema(
      accessMode = Schema.AccessMode.READ_WRITE,
      title = "Идентификатор пользователя Keycloak",
      example = "47496d84-73c7-4b39-9db6-bba8ac3b8581")
  @JsonProperty("keycloakId")
  @NotNull(message = "Please provide a keycloak id (keycloakId)")
  private UUID keycloakId;

  @Schema(
      accessMode = Schema.AccessMode.READ_WRITE,
      title = "Email/Login пользователя в Keycloak",
      example = "example@mail.zone")
  @JsonProperty("userName")
  @NotEmpty(message = "Please provide a userName (userName)")
  private String userName;

  @JsonProperty("roles")
  @NotNull(message = "Role list must not be null (roles)")
  private Set<UUID> userRoles;
}
