package ru.auchan.backend.controller.shared.response.role.model.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "[ROLE-MODEL] role model role response item")
public class RoleModelRoleResponseItem {

  @JsonProperty("id")
  private UUID roleId;

  @JsonProperty("name")
  private String roleName;
}
