package ru.auchan.backend.controller.permission;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.permission.metadata.PermissionGroupControllerMetadata;
import ru.auchan.backend.controller.permission.shared.request.PermissionGroupRequest;
import ru.auchan.backend.controller.permission.shared.response.PermissionGroupResponse;
import ru.auchan.backend.service.permission.IPermissionGroupService;

@RestController
@RequiredArgsConstructor
public class PermissionGroupController implements PermissionGroupControllerMetadata {

  private final IPermissionGroupService groupService;

  public ResponseEntity<List<PermissionGroupResponse>> getPermissionGroups() {
    return ResponseEntity.ok(groupService.findAll());
  }

  public ResponseEntity<PermissionGroupResponse> getById(final UUID id) {
    return ResponseEntity.ok(groupService.findById(id));
  }

  public ResponseEntity<PermissionGroupResponse> createNewPermissionGroup(
      @Valid @RequestBody final PermissionGroupRequest itemRequest) {
    return ResponseEntity.ok(groupService.add(itemRequest));
  }

  public ResponseEntity<Void> delete(final UUID id) {
    groupService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
