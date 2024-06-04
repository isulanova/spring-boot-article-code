package ru.auchan.backend.service.role.role_model.impl;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.role.shared.request.view.RoleModelPermissionRequestItem;
import ru.auchan.backend.controller.role.shared.request.view.RoleModelRoleRequestItem;
import ru.auchan.backend.controller.role.shared.request.view.RoleModelUpdatePermissionRequest;
import ru.auchan.backend.io.entity.PermissionEntity;
import ru.auchan.backend.io.entity.PermissionGroupEntity;
import ru.auchan.backend.io.entity.relations.RoleModelRelationEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;
import ru.auchan.backend.io.repository.RoleModelRepo;
import ru.auchan.backend.service.permission.IPermissionGroupService;
import ru.auchan.backend.service.permission.IPermissionService;
import ru.auchan.backend.service.permission.model.Permission;
import ru.auchan.backend.service.permission.model.PermissionGroup;
import ru.auchan.backend.service.role.IRoleService;
import ru.auchan.backend.service.role.exception.RoleModelException;
import ru.auchan.backend.service.role.role_model.IRoleModelUpdater;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleModelUpdater implements IRoleModelUpdater {

  private final RoleModelRepo roleModelRepo;

  private final IRoleService roleService;
  private final IPermissionGroupService permissionGroupService;
  private final IPermissionService permissionService;
  private final ModelMapper mapper;

  public static <T> Set<T> symmetricDifference(
      final Set<? extends T> s1, final Set<? extends T> s2) {
    final Set<T> symmetricDiff = new HashSet<>(s1);
    symmetricDiff.addAll(s2);
    final Set<T> tmp = new HashSet<>(s1);
    tmp.retainAll(s2);
    symmetricDiff.removeAll(tmp);
    return symmetricDiff;
  }

  @Override
  public Set<UUID> updatePermission(final RoleModelUpdatePermissionRequest roleModelRequest) {
    try {
      final var permissionGroup = getPermissionGroup(roleModelRequest);
      final var permission = getPermission(roleModelRequest.getGroup().getPermission());
      final List<ru.auchan.backend.service.role.model.Role> roles =
          getRoles(roleModelRequest.getGroup().getPermission().getRoles());
      log.info(
          "Try to update permission: {} from group: {}",
          permission.getSystemName(),
          permissionGroup.getName());

      final List<RoleModelRelationEntity> oldData = getOldState(permissionGroup, permission);
      final Set<RoleEntity> oldRoles =
          oldData.stream()
              .map(RoleModelRelationEntity::getRole)
              .filter(role -> !isNull(role))
              .collect(Collectors.toSet());
      roleModelRepo.deleteAll(oldData);

      final List<RoleModelRelationEntity> newData =
          createPersistentData(permissionGroup, permission, roles);
      final Set<RoleEntity> newRoles =
          newData.stream()
              .map(RoleModelRelationEntity::getRole)
              .filter(role -> !isNull(role))
              .collect(Collectors.toSet());
      log.info(
          "Old roles for permission: {} and group: {} - {}",
          permission.getSystemName(),
          permissionGroup.getName(),
          oldRoles.stream().map(RoleEntity::getName).toList());
      log.info(
          "New roles for permission: {} and group: {} - {}",
          permission.getSystemName(),
          permissionGroup.getName(),
          newRoles.stream().map(RoleEntity::getName).toList());
      if (!newData.isEmpty()) {
        final List<RoleModelRelationEntity> persistentData = roleModelRepo.saveAll(newData);
        log.info(
            "Update permission: {} from group: {} is success.New records count: {}",
            permission.getSystemName(),
            permissionGroup.getName(),
            persistentData.size());
      }
      // getting the set of affected role ids - the difference between new and old state
      final Set<UUID> oldRoleIds =
          oldRoles.stream().map(RoleEntity::getId).collect(Collectors.toSet());
      final Set<UUID> newRoleIds =
          newRoles.stream().map(RoleEntity::getId).collect(Collectors.toSet());
      return symmetricDifference(oldRoleIds, newRoleIds);
    } catch (final Exception ex) {
      final String message =
          String.format("Error while update role-model permission.Message: %s", ex.getMessage());
      log.error(message);
      throw new RoleModelException(message);
    }
  }

  private List<RoleModelRelationEntity> getOldState(
      final PermissionGroup permissionGroup, final Permission permission) {
    return roleModelRepo.findByPermissionAndPermissionGroup(
        mapper.map(permission, PermissionEntity.class),
        mapper.map(permissionGroup, PermissionGroupEntity.class));
  }

  private List<RoleModelRelationEntity> createPersistentData(
      final PermissionGroup permissionGroup,
      final Permission permission,
      final List<ru.auchan.backend.service.role.model.Role> roles) {
    final List<RoleModelRelationEntity> roleModelItems = new ArrayList<>();
    for (final ru.auchan.backend.service.role.model.Role role : roles) {
      log.info("Add role {} to permission {}", role.getSystemName(), permission.getSystemName());
      final var roleModelItem =
          RoleModelRelationEntity.builder()
              .role(mapper.map(role, RoleEntity.class))
              .permissionGroup(mapper.map(permissionGroup, PermissionGroupEntity.class))
              .permission(mapper.map(permission, PermissionEntity.class))
              .build();
      roleModelItems.add(roleModelItem);
    }
    return roleModelItems;
  }

  private List<ru.auchan.backend.service.role.model.Role> getRoles(
      final Set<RoleModelRoleRequestItem> roles) {
    if (isNull(roles) || roles.isEmpty()) {
      return Collections.emptyList();
    }
    return roleService.findAllById(
        roles.stream().map(RoleModelRoleRequestItem::getRoleId).toList());
  }

  private Permission getPermission(final RoleModelPermissionRequestItem requestItem) {
    final var permissionId = requestItem.getPermissionId();
    if (isNull(permissionId)) {
      final var message = "Permission id is null";
      log.error(message);
      throw new RoleModelException(message);
    } else {
      final var permissionGroup = permissionService.findByIdDb(permissionId);
      if (permissionGroup.isEmpty()) {
        final var message = String.format("Permission with id %s not found", permissionId);
        log.error(message);
        throw new RoleModelException(message);
      } else {
        return permissionGroup.get();
      }
    }
  }

  private PermissionGroup getPermissionGroup(
      final RoleModelUpdatePermissionRequest roleModelRequest) {
    final var permissionGroupId = roleModelRequest.getGroup().getGroupId();
    if (isNull(permissionGroupId)) {
      final var message = "Permission group id is null";
      log.error(message);
      throw new RoleModelException(message);
    } else {
      final var permissionGroup = permissionGroupService.findByIdDb(permissionGroupId);
      if (permissionGroup.isEmpty()) {
        final var message =
            String.format("Permission group with id %s not found", permissionGroupId);
        log.error(message);
        throw new RoleModelException(message);
      } else {
        return mapper.map(permissionGroup.get(), PermissionGroup.class);
      }
    }
  }
}
