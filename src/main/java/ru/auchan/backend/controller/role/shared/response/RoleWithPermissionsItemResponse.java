package ru.auchan.backend.controller.role.shared.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.controller.permission.shared.response.PermissionItemResponse;

@Getter
@Setter
@Schema(title = "[ROLE] role with permissions response")
public class RoleWithPermissionsItemResponse extends RoleItemResponse {

  private Set<PermissionItemResponse> permissions;
}
