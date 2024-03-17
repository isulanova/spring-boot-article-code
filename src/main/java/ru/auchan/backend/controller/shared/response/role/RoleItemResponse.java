package ru.auchan.backend.controller.shared.response.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.projection.RoleProj;

@Getter
@Setter
@Schema(title = "[ROLE] role item response")
public class RoleItemResponse extends RoleSimpleItemResponse {

  @JsonProperty("label")
  private String label;

  @JsonProperty("description")
  private String description;

  @Builder(builderMethodName = "roleItemResponseBuilder")
  public RoleItemResponse(
      final UUID id, final String systemName, final String label, final String description) {
    super(id, systemName);
    this.label = label;
    this.description = description;
  }

  public static RoleItemResponse fromProjection(final RoleProj roleProj) {
    return RoleItemResponse.roleItemResponseBuilder()
            .id(UUID.fromString(roleProj.getId()))
            .systemName(roleProj.getName())
            .description(roleProj.getDescription())
            .label(roleProj.getLabel())
            .build();
  }
}
