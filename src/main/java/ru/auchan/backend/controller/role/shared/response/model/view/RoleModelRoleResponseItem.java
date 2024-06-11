package ru.auchan.backend.controller.role.shared.response.model.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.service.role.model.Role;

@Getter
@Setter
@Builder
@Schema(title = "[ROLE-MODEL] role model role response item")
public class RoleModelRoleResponseItem implements Serializable {

  @JsonProperty("id")
  private UUID roleId;

  @JsonProperty("name")
  private String roleName;

  public static RoleModelRoleResponseItem fromModel(final Role role) {
    return RoleModelRoleResponseItem.builder()
        .roleId(role.getId())
        .roleName(role.getLabel())
        .build();
  }
}
