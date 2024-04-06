package ru.auchan.backend.service.role.role_model.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.service.permission.model.Permission;

@Getter
@Setter
public class PermissionGroupPermission {

  private UUID groupId;

  private String groupName;

  private String systemName;

  private Set<Permission> permission = new HashSet<>();
}
