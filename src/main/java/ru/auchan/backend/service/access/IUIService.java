package ru.auchan.backend.service.access;

import java.util.Set;
import java.util.UUID;
import ru.auchan.backend.controller.access.shared.request.AccessMapByPermissionGroupRequest;
import ru.auchan.backend.controller.access.shared.request.AccessRequest;
import ru.auchan.backend.service.access.model.UserAccessMap;

public interface IUIService {

  boolean checkAccess(AccessRequest permissionRequest);

  UserAccessMap getAccessMapByPermissionGroup(AccessMapByPermissionGroupRequest accessRequest);

  UserAccessMap getAccessMap(UUID userId);

  void renewAccessMap(UUID userId);

  void deleteCachedAccessMapForUsers(Set<UUID> userIds);
}
