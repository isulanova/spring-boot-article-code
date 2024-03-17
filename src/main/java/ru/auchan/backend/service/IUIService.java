package ru.auchan.backend.service;

import ru.auchan.backend.controller.shared.request.access.AccessMapByPermissionGroupRequest;
import ru.auchan.backend.controller.shared.request.access.AccessRequest;
import ru.auchan.backend.model.UserAccessMap;

import java.util.Set;
import java.util.UUID;

public interface IUIService {

  boolean checkAccess(AccessRequest permissionRequest);

  UserAccessMap getAccessMapByPermissionGroup(AccessMapByPermissionGroupRequest accessRequest);

  UserAccessMap getAccessMap(UUID userId);

  void renewAccessMap(UUID userId);

  void deleteCachedAccessMapForUsers(Set<UUID> userIds);
}
