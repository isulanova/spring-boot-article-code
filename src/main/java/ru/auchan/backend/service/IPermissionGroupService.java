package ru.auchan.backend.service;


import ru.auchan.backend.controller.shared.request.PermissionGroupRequest;
import ru.auchan.backend.controller.shared.response.PermissionGroupResponse;
import ru.auchan.backend.model.PermissionGroup;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface IPermissionGroupService {

  List<PermissionGroupResponse> findAllResponse();

  List<PermissionGroup> findAllData();

  Optional<PermissionGroupResponse> findByIdProj(UUID id);

  Optional<PermissionGroup> findByIdCache(UUID id);

  Optional<PermissionGroup> findByIdDb(UUID id);

  Optional<PermissionGroup> findBySystemNameDb(String systemName);

  boolean delete(UUID id);

  Optional<PermissionGroupResponse> add(PermissionGroupRequest request);

  Set<PermissionGroup> getPermissionsGroupByPermissionId(UUID permissionId);
}
