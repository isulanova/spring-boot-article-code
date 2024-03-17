package ru.auchan.backend.controller.shared.response.role;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.controller.shared.response.permission.PermissionItemResponse;

@Getter
@Setter
@Schema(title = "[ROLE] role with permissions response")
public class RoleWithPermissionsItemResponse extends RoleItemResponse {

  private Set<PermissionItemResponse> permissions;

  @Builder(builderMethodName = "roleItemWithPermissionResponseBuilder")
  public RoleWithPermissionsItemResponse(
      final UUID id,
      final String systemName,
      final String uiName,
      final String description,
      final Set<PermissionItemResponse> permissions) {
    super(id, systemName, uiName, description);
    this.permissions = permissions;
  }
}
