package ru.auchan.backend.service;

import java.util.Set;
import java.util.UUID;
import ru.auchan.backend.controller.shared.request.view.RoleModelUpdatePermissionRequest;
import ru.auchan.backend.controller.shared.response.role.model.system.RoleModelSystem;
import ru.auchan.backend.controller.shared.response.role.model.view.RoleModelResponse;

public interface IRoleModelService {

  Set<UUID> updatePermission(RoleModelUpdatePermissionRequest roleModelRequest);

  RoleModelResponse getRoleModel();

  RoleModelSystem getRoleModelSystem();

  void applyRoleModel(RoleModelSystem roleModelSystem);
}
