package ru.auchan.backend.service.role.impl;

import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.role.shared.request.view.RoleModelUpdatePermissionRequest;
import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystem;
import ru.auchan.backend.controller.role.shared.response.model.view.RoleModelResponse;
import ru.auchan.backend.service.role.IRoleModelService;
import ru.auchan.backend.service.role.role_model.IRoleModelApplier;
import ru.auchan.backend.service.role.role_model.IRoleModelBuilder;
import ru.auchan.backend.service.role.role_model.IRoleModelUpdater;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleModelService implements IRoleModelService {

  private final IRoleModelBuilder modelBuilder;
  private final IRoleModelApplier modelApplier;
  private final IRoleModelUpdater modelUpdater;

  // @Transactional
  @Override
  // @CacheEvict(value = "role_model", key = "'" + "role_model" + "'")
  public Set<UUID> updatePermission(final RoleModelUpdatePermissionRequest roleModelRequest) {
    final Set<UUID> affectedRoleIds = modelUpdater.updatePermission(roleModelRequest);
    rebuildRoleModel();
    return affectedRoleIds;
  }

  @Override
  // @Cacheable(value = "role_model", key = "'" + "role_model" + "'")
  public RoleModelResponse getRoleModel() {
    return modelBuilder.getRoleModelUi();
  }

  @Override
  public RoleModelSystem getRoleModelSystem() {
    return modelBuilder.getRoleModelSystem();
  }

  // @Transactional
  @Override
  // @CacheEvict(value = "role_model", key = "'" + "role_model" + "'")
  public void applyRoleModel(final RoleModelSystem roleModelSystem) {
    modelApplier.applyRoleModel(roleModelSystem);
    rebuildRoleModel();
  }

  private void rebuildRoleModel() {
    modelBuilder.getRoleModelUi();
  }
}
