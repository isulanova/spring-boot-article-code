package ru.auchan.backend.service.role.role_model.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoleModelRaw {

  @Builder.Default
  private Map<String, PermissionGroupPermission> permissionByGroup = new HashMap<>();

  @Builder.Default private Map<String, PermissionRoleGroup> rolesByPermission = new HashMap<>();
}
