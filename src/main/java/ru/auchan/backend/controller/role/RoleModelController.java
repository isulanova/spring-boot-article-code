package ru.auchan.backend.controller.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.role.metadata.RoleModelControllerMetadata;
import ru.auchan.backend.controller.role.shared.request.view.RoleModelUpdatePermissionRequest;
import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystem;
import ru.auchan.backend.controller.role.shared.response.model.view.RoleModelResponse;
import ru.auchan.backend.service.user.IAuthUserService;
import ru.auchan.backend.service.role.IRoleModelService;
import ru.auchan.backend.service.access.IUIService;


import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RoleModelController implements RoleModelControllerMetadata {

  private final IRoleModelService roleModelService;
  private final IAuthUserService authUserService;
  private final IUIService uiService;

  @Override
  public ResponseEntity<Void> updatePermission(final RoleModelUpdatePermissionRequest roleModelRequest) {
    final Set<UUID> affectedRoleIds = roleModelService.updatePermission(roleModelRequest);
    final Set<UUID> userIds = authUserService.findUsersByRoleId(new ArrayList<>(affectedRoleIds));
    uiService.deleteCachedAccessMapForUsers(userIds);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @Override
  public ResponseEntity<RoleModelResponse> getRoleModel() {
    return ResponseEntity.ok(roleModelService.getRoleModel());
  }

  @Override
  public ResponseEntity<RoleModelSystem> getRoleModelSystem() {
    return ResponseEntity.ok(roleModelService.getRoleModelSystem());
  }

  @Override
  public ResponseEntity<Void> applyRoleModel(final RoleModelSystem roleModelSystem) {
    roleModelService.applyRoleModel(roleModelSystem);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }
}
