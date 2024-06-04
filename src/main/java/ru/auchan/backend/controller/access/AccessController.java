package ru.auchan.backend.controller.access;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.access.metadata.AccessControllerMetadata;
import ru.auchan.backend.controller.access.shared.request.AccessMapByPermissionGroupRequest;
import ru.auchan.backend.controller.access.shared.request.AccessRequest;
import ru.auchan.backend.controller.access.shared.response.UserAccessMapResponse;
import ru.auchan.backend.service.access.IUIService;

@RestController
@RequiredArgsConstructor
public class AccessController implements AccessControllerMetadata {

  private final IUIService uiService;
  private final ModelMapper mapper;

  @Override
  public UserAccessMapResponse getAccessMap(final UUID userId) {
    return mapper.map(uiService.getAccessMap(userId), UserAccessMapResponse.class);
  }

  @Override
  public void renewAccessMap(final UUID userId) {
    uiService.renewAccessMap(userId);
  }

  @Override
  public UserAccessMapResponse getAccessMapByPermissionGroup(
      final AccessMapByPermissionGroupRequest accessRequest) {
    return mapper.map(
        uiService.getAccessMapByPermissionGroup(accessRequest), UserAccessMapResponse.class);
  }

  @Override
  public Boolean checkAccess(final AccessRequest permissionRequest) {
    final boolean accessGranted = uiService.checkAccess(permissionRequest);
    if (accessGranted) {
      return Boolean.TRUE;
    } else {
      return Boolean.FALSE;
    }
  }
}
