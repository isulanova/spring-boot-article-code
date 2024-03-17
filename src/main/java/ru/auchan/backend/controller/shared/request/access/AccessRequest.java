package ru.auchan.backend.controller.shared.request.access;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Schema(
    title = "[ACCESS] Access by permission request",
    description = "Checking the ability to access a functional element")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class AccessRequest {

  @Schema(
      accessMode = Schema.AccessMode.READ_WRITE,
      title = "Keycloak User ID",
      example = "47496d84-73c7-4b39-9db6-bba8ac3b8581")
  @NotNull(message = "Please provide a keycloak id (keycloakId)")
  @JsonProperty("keycloakId")
  private UUID keycloakId;

  @Schema(
      accessMode = Schema.AccessMode.READ_WRITE,
      title = "Privilege system name",
      example = "CL_VIEW_DOCS")
  @JsonProperty("permission")
  private List<String> permission;

  @Schema(accessMode = Schema.AccessMode.READ_WRITE, title = "Role system name", example = "ADMIN")
  @JsonProperty("role")
  private List<String> role;
}
