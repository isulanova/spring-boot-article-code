package ru.auchan.backend.controller.shared.response.role.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "[ROLE-MODEL] role model permission response item")
public class RoleModelPermissionResponseItem {

  @JsonProperty("id")
  private UUID permissionId;

  @JsonProperty("name")
  private String permissionName;

  @JsonProperty("description")
  private String description;

  @JsonIgnore @Getter private String systemName;

  @Builder.Default
  @JsonProperty("roles")
  private Set<RoleModelRoleResponseItem> roles = new HashSet<>();
}
