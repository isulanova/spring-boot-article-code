package ru.auchan.backend.controller.role.shared.request.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "[ROLE] role item simple response")
public class RoleItemBase implements Serializable {

  @JsonProperty("id")
  private UUID roleId;

  @JsonProperty("name")
  private String name;
}
