package ru.auchan.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.auchan.backend.controller.shared.request.permission.PermissionItemRequest;
import ru.auchan.backend.controller.shared.response.permission.PermissionItemResponse;
import ru.auchan.backend.model.Permission;

import java.util.List;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public interface IPermissionService {

  Page<PermissionItemResponse> list(Pageable pageable);

  List<Permission> list();

  List<Permission> getPermissionListByPermissionGroupList(List<String> groupList);

  Optional<Permission> findByIdDb(UUID id);

  Optional<Permission> findBySystemName(String permissionName);

  Set<String> findByRoleIds(List<UUID> roleIds);

  Optional<PermissionItemResponse> findById(UUID id);

  Optional<PermissionItemResponse> addPermission(PermissionItemRequest itemRequest);

  boolean removePermission(UUID id);

  Optional<PermissionItemResponse> updatePermission(UUID id, PermissionItemRequest itemRequest);
}
