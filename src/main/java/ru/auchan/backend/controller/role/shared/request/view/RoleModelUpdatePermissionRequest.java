package ru.auchan.backend.controller.role.shared.request.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "[ROLE-MODEL] role model update permission request")
public class RoleModelUpdatePermissionRequest {

  @NotNull(message = "Please provide permission group (permissionGroup)")
  @JsonProperty("permissionGroup")
  private RoleModelGroupRequestItem group;
}
