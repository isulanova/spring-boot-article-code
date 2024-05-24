package ru.auchan.backend.controller.role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.role.metadata.RoleControllerMetadata;
import ru.auchan.backend.controller.role.shared.request.RoleItemRequest;
import ru.auchan.backend.controller.role.shared.response.RoleItemAdminResponse;
import ru.auchan.backend.controller.role.shared.response.RoleItemResponse;
import ru.auchan.backend.controller.role.shared.response.RoleWithPermissionsItemResponse;
import ru.auchan.backend.controller.role.shared.response.RolesByUserResponse;
import ru.auchan.backend.service.role.IRoleService;

@RestController
@RequiredArgsConstructor
public class RoleController implements RoleControllerMetadata {

  private final IRoleService roleService;

  public ResponseEntity<List<RoleItemAdminResponse>> roleList() {
    return ResponseEntity.ok(roleService.getRoleList());
  }

  @Override
  public ResponseEntity<RoleWithPermissionsItemResponse> findBySystemName(final String systemName) {
    final var roleOptional = roleService.findBySystemName(systemName.toUpperCase());
    return roleOptional
        .map(role -> ResponseEntity.status(HttpStatus.OK).body(role))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  public ResponseEntity<RolesByUserResponse> roleListByKeycloakId(final UUID id) {
    final Optional<RolesByUserResponse> response = roleService.getRoleListByKeycloakId(id);
    return response
        .map(
            roleByKeycloakIdResponse ->
                ResponseEntity.status(HttpStatus.OK).body(roleByKeycloakIdResponse))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  public ResponseEntity<RoleWithPermissionsItemResponse> roleWithPermissionsByRoleId(
      final UUID id) {
    final Optional<RoleWithPermissionsItemResponse> response =
        roleService.getRoleWithPermission(id);
    return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  public ResponseEntity<RoleItemResponse> createNewRole(final RoleItemRequest itemRequest) {
    final Optional<RoleItemResponse> roleProj = roleService.addRole(itemRequest);
    return roleProj
        .map(proj -> ResponseEntity.status(HttpStatus.CREATED).body(proj))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  public ResponseEntity<Void> deleteRole(final UUID id) {
    final boolean deleteResult = roleService.removeRole(id);
    if (deleteResult) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  public ResponseEntity<RoleItemResponse> updateRole(
      final UUID id, final RoleItemRequest itemRequest) {
    final Optional<RoleItemResponse> roleProj = roleService.updateRole(id, itemRequest);
    return roleProj
        .map(proj -> ResponseEntity.status(HttpStatus.ACCEPTED).body(proj))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
