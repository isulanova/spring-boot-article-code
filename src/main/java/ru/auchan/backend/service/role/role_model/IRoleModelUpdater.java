package ru.auchan.backend.service.role.role_model;

import java.util.Set;
import java.util.UUID;
import ru.auchan.backend.controller.role.shared.request.view.RoleModelUpdatePermissionRequest;

public interface IRoleModelUpdater {

  Set<UUID> updatePermission(RoleModelUpdatePermissionRequest roleModelRequest);
}
