package ru.auchan.backend.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.metadata.AccessControllerMetadata;
import ru.auchan.backend.controller.shared.request.access.AccessMapByPermissionGroupRequest;
import ru.auchan.backend.controller.shared.request.access.AccessRequest;
import ru.auchan.backend.controller.shared.response.UserAccessMapResponse;
import ru.auchan.backend.service.IUIService;

@RestController
@RequiredArgsConstructor
public class AccessController implements AccessControllerMetadata {

  private final IUIService uiService;
  private final ModelMapper mapper;

  @Override
  public ResponseEntity<UserAccessMapResponse> getAccessMap(final UUID userId) {
    return ResponseEntity.ok(
        mapper.map(uiService.getAccessMap(userId), UserAccessMapResponse.class));
  }

  @Override
  public ResponseEntity<UserAccessMapResponse> renewAccessMap(final UUID userId) {
     uiService.renewAccessMap(userId);
     return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<UserAccessMapResponse> getAccessMapByPermissionGroup(
      final AccessMapByPermissionGroupRequest accessRequest) {
    return ResponseEntity.ok(
        mapper.map(
            uiService.getAccessMapByPermissionGroup(accessRequest), UserAccessMapResponse.class));
  }

  @Override
  public ResponseEntity<Boolean> checkAccess(final AccessRequest permissionRequest) {
    final boolean accessGranted = uiService.checkAccess(permissionRequest);
    if (accessGranted) {
      return ResponseEntity.ok(Boolean.TRUE);
    } else {
      return ResponseEntity.ok(Boolean.FALSE);
    }
  }
}
