package ru.auchan.backend.controller.access.shared.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "[ACCESS] Access map by permission group request")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessMapByPermissionGroupRequest {

  @Schema(
      accessMode = Schema.AccessMode.READ_WRITE,
      title = "Keycloak User ID",
      example = "47496d84-73c7-4b39-9db6-bba8ac3b8581")
  @NotNull(message = "Please provide a keycloak id (keycloakId)")
  @JsonProperty("keycloakId")
  private UUID keycloakId;

  @Schema(
      accessMode = Schema.AccessMode.READ_WRITE,
      title = "Privilege group ID",
      example = "47496d84-73c7-4b39-9db6-bba8ac3b8581")
  @NotNull(message = "Please provide a permission group id (permissionGroupId)")
  @JsonProperty("permissionGroupId")
  private UUID permissionGroupId;
}
