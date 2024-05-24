package ru.auchan.backend.service.role.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.service.permission.model.Permission;
import ru.auchan.backend.service.permission.model.PermissionGroup;

@Getter
@Setter
public class RoleModel {

  private UUID id;

  private Role role;

  private Permission permission;

  private PermissionGroup permissionGroup;
}
