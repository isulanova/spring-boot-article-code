package ru.auchan.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoleModel {

    private UUID id;

    private Role role;

    private Permission permission;

    private PermissionGroup permissionGroup;
}
