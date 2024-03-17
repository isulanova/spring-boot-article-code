package ru.auchan.backend.controller.shared.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;

@Getter
@Schema(title = "[USER] user roles update request")
public class AuthUserUpdateRequest {

  @JsonProperty("userName")
  private String userName;

  @JsonProperty("keycloakId")
  private UUID keycloakId;

  @JsonProperty("roles")
  private Set<UUID> userRoles;
}
