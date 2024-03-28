package ru.auchan.backend.controller.role.shared.response.model.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "[ROLE-MODEL] role model group response item")
public class RoleModelGroupResponseItem {

  @JsonProperty("id")
  private UUID groupId;

  @JsonProperty("name")
  private String groupName;

  @Builder.Default
  @JsonProperty("permissions")
  private Set<RoleModelPermissionResponseItem> permissions = new TreeSet<>();
}
