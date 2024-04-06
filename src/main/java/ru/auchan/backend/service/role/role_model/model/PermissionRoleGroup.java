package ru.auchan.backend.service.role.role_model.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.service.permission.model.Permission;
import ru.auchan.backend.service.permission.model.PermissionGroup;
import ru.auchan.backend.service.role.model.Role;

@Getter
@Setter
public class PermissionRoleGroup {

  private PermissionGroup permissionGroup;

  private Permission permission;

  private Set<Role> roles = new HashSet<>();
}
