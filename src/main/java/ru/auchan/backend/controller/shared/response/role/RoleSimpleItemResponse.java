package ru.auchan.backend.controller.shared.response.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "[ROLE] role item simple response")
public class RoleSimpleItemResponse implements Serializable {

  @JsonProperty("id")
  private UUID roleId;

  @JsonProperty("name")
  private String name;

  public RoleSimpleItemResponse() {

  }

  @Builder
  public RoleSimpleItemResponse(final UUID id, final String name) {
    this.roleId = id;
    this.name = name;
  }
}
