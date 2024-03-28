package ru.auchan.backend.controller.permission.shared.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.auchan.backend.io.entity.PermissionEntity;
import ru.auchan.backend.io.projection.PermissionProj;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "[PERMISSION] Permission item response")
public class PermissionItemResponse {

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("systemName")
  private String systemName;

  @JsonProperty("uiName")
  private String uiName;

  @JsonProperty("description")
  private String description;

  public static PermissionItemResponse fromProjection(final PermissionProj permissionProj) {
    return PermissionItemResponse.builder()
        .id(UUID.fromString(permissionProj.getId()))
        .description(permissionProj.getDescription())
        .systemName(permissionProj.getSystemName())
        .uiName(permissionProj.getUiName())
        .build();
  }

  public static PermissionItemResponse fromEntity(final PermissionEntity permission) {
    return PermissionItemResponse.builder()
        .id(permission.getId())
        .description(permission.getDescription())
        .systemName(permission.getSystemName())
        .uiName(permission.getUiName())
        .build();
  }
}
