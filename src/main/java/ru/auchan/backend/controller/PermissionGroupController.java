package ru.auchan.backend.controller;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.metadata.PermissionGroupControllerMetadata;
import ru.auchan.backend.controller.shared.request.PermissionGroupRequest;
import ru.auchan.backend.controller.shared.response.PermissionGroupResponse;
import ru.auchan.backend.service.IPermissionGroupService;

@RestController
@RequiredArgsConstructor
public class PermissionGroupController implements PermissionGroupControllerMetadata {

  private final IPermissionGroupService groupService;

  public ResponseEntity<List<PermissionGroupResponse>> getPermissionGroups() {
    return ResponseEntity.ok(groupService.findAllResponse());
  }

  public ResponseEntity<PermissionGroupResponse> getById(final UUID id) {
    final Optional<PermissionGroupResponse> response = groupService.findByIdProj(id);
    return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  public ResponseEntity<PermissionGroupResponse> createNewPermissionGroup(
      @Valid @RequestBody final PermissionGroupRequest itemRequest) {
    final Optional<PermissionGroupResponse> roleProj = groupService.add(itemRequest);
    return roleProj
        .map(proj -> ResponseEntity.status(HttpStatus.CREATED).body(proj))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  public ResponseEntity<String> delete(final UUID id) {
    final boolean deleteResult = groupService.delete(id);
    if (deleteResult) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
