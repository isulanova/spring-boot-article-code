package ru.auchan.backend.controller.role.shared.request.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "[ROLE-MODEL] role model role request")
public class RoleModelRoleRequestItem {

  @JsonProperty("id")
  private UUID roleId;
}
