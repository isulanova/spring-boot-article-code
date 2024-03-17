package ru.auchan.backend.controller.shared.request.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "[ROLE-MODEL] role model permission request")
public class RoleModelPermissionRequestItem {

  @NotNull(message = "Please provide permission id (id)")
  @JsonProperty("id")
  private UUID permissionId;

  @JsonProperty("roles")
  private Set<RoleModelRoleRequestItem> roles;
}
