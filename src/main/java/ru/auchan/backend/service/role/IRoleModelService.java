package ru.auchan.backend.service.role;

import java.util.Set;
import java.util.UUID;
import ru.auchan.backend.controller.shared.request.view.RoleModelUpdatePermissionRequest;
import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystem;
import ru.auchan.backend.controller.role.shared.response.model.view.RoleModelResponse;

public interface IRoleModelService {

  Set<UUID> updatePermission(RoleModelUpdatePermissionRequest roleModelRequest);

  RoleModelResponse getRoleModel();

  RoleModelSystem getRoleModelSystem();

  void applyRoleModel(RoleModelSystem roleModelSystem);
}
