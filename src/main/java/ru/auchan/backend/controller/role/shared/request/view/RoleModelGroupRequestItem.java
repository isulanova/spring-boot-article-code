package ru.auchan.backend.controller.role.shared.request.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;

@Getter
@Schema(title = "[ROLE-MODEL] role model group request")
public class RoleModelGroupRequestItem {

  @NotNull(message = "Please provide permission group id (id)")
  @JsonProperty("id")
  private UUID groupId;

  @NotNull(message = "Please provide permission (permission)")
  @JsonProperty("permission")
  private RoleModelPermissionRequestItem permission;
}
