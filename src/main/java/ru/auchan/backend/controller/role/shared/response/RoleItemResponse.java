package ru.auchan.backend.controller.role.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(title = "[ROLE] role item response")
public class RoleItemResponse extends RoleSimpleItemResponse {

  @JsonProperty("label")
  private String label;

  @JsonProperty("description")
  private String description;
}
