package ru.auchan.backend.service.role.model;

import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.service.permission.model.Permission;
import ru.auchan.backend.service.permission.model.PermissionGroup;

import java.util.UUID;

@Getter
@Setter
public class RoleModel {

    private UUID id;

    private Role role;

    private Permission permission;

    private PermissionGroup permissionGroup;
}
