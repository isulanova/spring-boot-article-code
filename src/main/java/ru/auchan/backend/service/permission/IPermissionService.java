package ru.auchan.backend.service.permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.auchan.backend.controller.permission.shared.request.PermissionItemRequest;
import ru.auchan.backend.controller.permission.shared.response.PermissionItemResponse;
import ru.auchan.backend.service.permission.model.Permission;

public interface IPermissionService {

  Page<PermissionItemResponse> list(Pageable pageable);

  List<Permission> list();

  List<Permission> getPermissionListByPermissionGroupList(List<String> groupList);

  Optional<Permission> findBySystemName(String permissionName);

  Optional<PermissionItemResponse> findById(UUID id);

  Optional<PermissionItemResponse> addPermission(PermissionItemRequest itemRequest);

  void removePermission(UUID id);

  Optional<PermissionItemResponse> updatePermission(UUID id, PermissionItemRequest itemRequest);
}
