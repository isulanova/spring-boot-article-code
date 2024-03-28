package ru.auchan.backend.service.access.impl;

import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.access.shared.request.AccessMapByPermissionGroupRequest;
import ru.auchan.backend.controller.access.shared.request.AccessRequest;
import ru.auchan.backend.service.access.model.UserAccessMap;
import ru.auchan.backend.service.access.IUIService;

@Slf4j
@Service
public class UIService implements IUIService {
  @Override
  public boolean checkAccess(AccessRequest permissionRequest) {
    return false;
  }

  @Override
  public UserAccessMap getAccessMapByPermissionGroup(
      AccessMapByPermissionGroupRequest accessRequest) {
    return null;
  }

  @Override
  public UserAccessMap getAccessMap(UUID userId) {
    return null;
  }

  @Override
  public void renewAccessMap(UUID userId) {}

  @Override
  public void deleteCachedAccessMapForUsers(Set<UUID> userIds) {}
}
