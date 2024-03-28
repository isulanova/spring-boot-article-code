package ru.auchan.backend.controller.role.shared.response.model.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "[ROLE-MODEL] role model response")
public class RoleModelResponse implements Serializable {

  @JsonProperty("permissionGroup")
  private Set<RoleModelGroupResponseItem> group = new HashSet<>();
}
