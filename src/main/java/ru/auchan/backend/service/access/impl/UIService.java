package ru.auchan.backend.service.access.impl;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.access.shared.request.AccessMapByPermissionGroupRequest;
import ru.auchan.backend.controller.access.shared.request.AccessRequest;
import ru.auchan.backend.controller.role.shared.response.RoleItemResponse;
import ru.auchan.backend.controller.role.shared.response.RoleSimpleItemResponse;
import ru.auchan.backend.controller.user.shared.response.AuthUserItemWithRolesResponse;
import ru.auchan.backend.io.entity.PermissionEntity;
import ru.auchan.backend.io.entity.relations.RoleModelRelationEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;
import ru.auchan.backend.io.repository.RoleModelRepo;
import ru.auchan.backend.service.access.IUIService;
import ru.auchan.backend.service.access.model.UserAccessMap;
import ru.auchan.backend.service.role.IRoleService;
import ru.auchan.backend.service.user.IAuthUserService;
import ru.auchan.modules.map.mapping.GeneralModelMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class UIService implements IUIService {

  private final IAuthUserService userService;
  private final IRoleService roleService;
  private final GeneralModelMapper modelMapper;
  private final RoleModelRepo roleModelRepo;

  @Override
  public boolean checkAccess(AccessRequest accessRequest) {
    final UserAccessMap accessMap = getAccessMap(accessRequest.getKeycloakId());
    if (isNull(accessMap)) {
      return false;
    }

    if (isNullOrEmpty(accessRequest.getRole()) && isNullOrEmpty(accessRequest.getPermission())) {
      return false;
    } else if (!isNullOrEmpty(accessRequest.getRole())
        && isNullOrEmpty(accessRequest.getPermission())) {
      return hasRoles(accessMap, accessRequest.getRole());
    } else if (isNullOrEmpty(accessRequest.getRole())
        && !isNullOrEmpty(accessRequest.getPermission())) {
      return hasPermissions(accessMap, accessRequest.getPermission());
    } else {
      return hasRoles(accessMap, accessRequest.getRole())
          && hasPermissions(accessMap, accessRequest.getPermission());
    }
  }

  private boolean isNullOrEmpty(final List<String> list) {
    return isNull(list) || list.isEmpty();
  }

  @Override
  public UserAccessMap getAccessMapByPermissionGroup(
      AccessMapByPermissionGroupRequest accessRequest) {
    // todo
    return null;
  }

  @Override
  public UserAccessMap getAccessMap(UUID userId) {
    final Optional<AuthUserItemWithRolesResponse> userFromDb = getUserFromDb(userId);
    if (userFromDb.isEmpty()) {
      log.error("User with id {} not found.Generate empty accessMap", userId);
      return createEmptyAccessMap(userId);
    } else {
      return generateUserAccessMap(userFromDb.get());
    }
  }

  @Override
  public void renewAccessMap(UUID userId) {
    final Optional<AuthUserItemWithRolesResponse> userFromDb = getUserFromDb(userId);
    if (userFromDb.isEmpty()) {
      log.error("User with id {} not found. can't renew access map!", userId);
    } else {
      // deleteCachedAccessMap(userId);
      generateUserAccessMap(userFromDb.get());
    }
  }

  @Override
  public void deleteCachedAccessMapForUsers(Set<UUID> userIds) {
    // todo
  }

  private UserAccessMap generateUserAccessMap(final AuthUserItemWithRolesResponse userFromDb) {
    final List<RoleItemResponse> roles = getUserRoles(userFromDb);
    if (roles.isEmpty()) {
      return createEmptyAccessMap(userFromDb.getKeycloakId());
    } else {
      final List<String> roleList = roles.stream().map(RoleItemResponse::getName).toList();
      final List<UUID> roleIds = roles.stream().map(RoleSimpleItemResponse::getRoleId).toList();
      return createAccessMap(
          userFromDb.getKeycloakId().toString(), getUserPermissions(roleIds), roleList);
    }
  }

  private Optional<AuthUserItemWithRolesResponse> getUserFromDb(final UUID userId) {
    return userService.findByKeycloakId(userId);
  }

  private List<RoleItemResponse> getUserRoles(final AuthUserItemWithRolesResponse userFromDb) {
    return new ArrayList<>(userFromDb.getRoles());
  }

  private List<String> getUserPermissions(final List<UUID> roleIds) {

    return roleModelRepo
        .findByRoleIn(
            roleService.findAllById(roleIds).stream()
                .map(item -> modelMapper.map(item, RoleEntity.class))
                .toList())
        .stream()
        .map(RoleModelRelationEntity::getPermission)
        .map(PermissionEntity::getSystemName)
        .distinct()
        .toList();
  }

  private UserAccessMap createEmptyAccessMap(final UUID userId) {
    return new UserAccessMap(userId.toString(), Collections.emptyList(), Collections.emptyList());
  }

  private UserAccessMap createAccessMap(
      final String userId, final List<String> permissions, final List<String> roles) {
    return new UserAccessMap(userId, permissions, roles);
  }

  private boolean hasRoles(final UserAccessMap accessMap, final List<String> role) {
    return accessMap.getUserRoles().stream().anyMatch(role::contains);
  }

  private boolean hasPermissions(final UserAccessMap accessMap, final List<String> permission) {
    return accessMap.getPermissions().stream().anyMatch(permission::contains);
  }
}
