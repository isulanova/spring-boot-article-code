package ru.auchan.backend.service.permission;

import java.util.List;
import java.util.UUID;
import ru.auchan.backend.controller.permission.shared.request.PermissionGroupRequest;
import ru.auchan.backend.controller.permission.shared.response.PermissionGroupResponse;

public interface IPermissionGroupService {

  List<PermissionGroupResponse> findAll();

  PermissionGroupResponse findById(UUID id);

  void delete(UUID id);

  PermissionGroupResponse add(PermissionGroupRequest request);
}
