package ru.auchan.backend.service.role.role_model.impl;

import static java.util.Objects.isNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystem;
import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystemGroup;
import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystemPermission;
import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystemRole;
import ru.auchan.backend.controller.role.shared.response.model.view.RoleModelGroupResponseItem;
import ru.auchan.backend.controller.role.shared.response.model.view.RoleModelPermissionResponseItem;
import ru.auchan.backend.controller.role.shared.response.model.view.RoleModelResponse;
import ru.auchan.backend.controller.role.shared.response.model.view.RoleModelRoleResponseItem;
import ru.auchan.backend.io.entity.PermissionEntity;
import ru.auchan.backend.io.entity.relations.RoleModelRelationEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;
import ru.auchan.backend.io.repository.RoleModelRepo;
import ru.auchan.backend.service.permission.model.Permission;
import ru.auchan.backend.service.role.exception.RoleModelException;
import ru.auchan.backend.service.role.model.Role;
import ru.auchan.backend.service.role.role_model.IRoleModelBuilder;
import ru.auchan.backend.service.role.role_model.model.PermissionGroupPermission;
import ru.auchan.backend.service.role.role_model.model.PermissionRoleGroup;
import ru.auchan.backend.service.role.role_model.model.RoleModelRaw;
import ru.auchan.modules.map.mapping.GeneralModelMapper;

@SuppressWarnings("checkstyle:classdataabstractioncoupling")
@Slf4j
@Component
@RequiredArgsConstructor
public class RoleModelBuilder implements IRoleModelBuilder {

  private final RoleModelRepo roleModelRepo;
  private final GeneralModelMapper mapper;

  @Override
  public RoleModelResponse getRoleModelUi() {
    final RoleModelRaw roleModelRaw = prepareRoleModel();
    final RoleModelResponse response = new RoleModelResponse();
    response.setGroup(createGroupItemsUi(roleModelRaw));
    return response;
  }

  @Override
  public RoleModelSystem getRoleModelSystem() {
    final RoleModelRaw roleModelRaw = prepareRoleModel();
    final RoleModelSystem response = new RoleModelSystem();
    response.setGroup(createGroupItemsSystem(roleModelRaw));
    return response;
  }

  private Set<RoleModelSystemGroup> createGroupItemsSystem(final RoleModelRaw roleModelRaw) {
    try {
      final Set<RoleModelSystemGroup> responseItemSet = new TreeSet<>();
      for (final Map.Entry<String, PermissionGroupPermission> entry :
          roleModelRaw.getPermissionByGroup().entrySet()) {
        final RoleModelSystemGroup groupResponseItem = new RoleModelSystemGroup();
        final PermissionGroupPermission groupPermission = entry.getValue();
        groupResponseItem.setSystemName(groupPermission.getSystemName());
        groupResponseItem.setPermissions(
            createPermissionSystem(roleModelRaw.getRolesByPermission(), groupPermission));
        responseItemSet.add(groupResponseItem);
      }
      return responseItemSet;
    } catch (final RoleModelException ex) {
      final String message =
          String.format("Error while create system role model.Message: %s", ex.getMessage());
      log.error(message);
      throw new RoleModelException(message);
    }
  }

  private Set<RoleModelSystemPermission> createPermissionSystem(
      final Map<String, PermissionRoleGroup> permissionRoleMap,
      final PermissionGroupPermission groupPermission) {
    try {
      log.info("Create permissions by group: {}", groupPermission.getGroupName());
      final Set<RoleModelSystemPermission> permissionResponseItems =
          groupPermission.getPermission().stream()
              .map(this::createPermissionItemSystem)
              .collect(Collectors.toSet());
      log.info("Fill roles by group: {}", groupPermission.getGroupName());
      permissionResponseItems.forEach(
          item -> item.setRoles(createRolesSystem(permissionRoleMap, item.getSystemName())));
      log.info("Hierarchy object by group: {} created success", groupPermission.getGroupName());
      return permissionResponseItems;
    } catch (final RoleModelException ex) {
      throw new RoleModelException("Permission set create error: " + ex.getMessage());
    }
  }

  private RoleModelSystemPermission createPermissionItemSystem(final Permission p) {
    final var roleModelSystemPermission = new RoleModelSystemPermission();
    roleModelSystemPermission.setSystemName(p.getSystemName());
    return roleModelSystemPermission;
  }

  private Set<RoleModelSystemRole> createRolesSystem(
      final Map<String, PermissionRoleGroup> permissionByRole, final String permissionKey) {
    try {
      final PermissionRoleGroup permissionRole = permissionByRole.get(permissionKey);
      if (isNull(permissionRole) || permissionRole.getRoles().isEmpty()) {
        return Collections.emptySet();
      }
      final Set<RoleModelSystemRole> returnItems =
          permissionRole.getRoles().stream()
              .filter(r -> !isNull(r))
              .map(RoleModelSystemRole::fromModel)
              .collect(Collectors.toSet());
      log.info("Add {} roles to permission: {}", returnItems.size(), permissionKey);
      return returnItems;
    } catch (final Exception ex) {
      throw new RoleModelException("Role set create error: " + ex.getMessage());
    }
  }

  private Set<RoleModelGroupResponseItem> createGroupItemsUi(final RoleModelRaw roleModelRaw) {
    final Set<RoleModelGroupResponseItem> responseItemSet = new TreeSet<>();
    for (final Map.Entry<String, PermissionGroupPermission> entry :
        roleModelRaw.getPermissionByGroup().entrySet()) {
      final RoleModelGroupResponseItem groupResponseItem = new RoleModelGroupResponseItem();
      final PermissionGroupPermission groupPermission = entry.getValue();
      groupResponseItem.setGroupId(groupPermission.getGroupId());
      groupResponseItem.setGroupName(groupPermission.getGroupName());
      groupResponseItem.setPermissions(
          createPermissionsUi(roleModelRaw.getRolesByPermission(), groupPermission));
      responseItemSet.add(groupResponseItem);
    }
    return responseItemSet;
  }

  private RoleModelPermissionResponseItem createPermissionItemUi(final Permission p) {
    return RoleModelPermissionResponseItem.builder()
        .permissionId(p.getId())
        .permissionName(p.getUiName())
        .description(p.getDescription())
        .systemName(p.getSystemName())
        .build();
  }

  private Set<RoleModelPermissionResponseItem> createPermissionsUi(
      final Map<String, PermissionRoleGroup> permissionRoleMap,
      final PermissionGroupPermission groupPermission) {
    final Set<RoleModelPermissionResponseItem> permissionResponseItems =
        groupPermission.getPermission().stream()
            .map(this::createPermissionItemUi)
            .collect(Collectors.toCollection(TreeSet::new));
    permissionResponseItems.forEach(
        item -> item.setRoles(createRolesUi(permissionRoleMap, item.getSystemName())));
    return permissionResponseItems;
  }

  private Set<RoleModelRoleResponseItem> createRolesUi(
      final Map<String, PermissionRoleGroup> permissionByRole, final String permissionKey) {
    final PermissionRoleGroup permissionRole = permissionByRole.get(permissionKey);
    if (isNull(permissionRole) || permissionRole.getRoles().isEmpty()) {
      return Collections.emptySet();
    }
    return permissionRole.getRoles().stream()
        .filter(r -> !isNull(r))
        .map(RoleModelRoleResponseItem::fromModel)
        .collect(Collectors.toSet());
  }

  private RoleModelRaw prepareRoleModel() {
    final Map<String, PermissionGroupPermission> permissionByGroup = new HashMap<>();
    final Map<String, PermissionRoleGroup> rolesByPermission = new HashMap<>();

    final List<RoleModelRelationEntity> roleModelItems = roleModelRepo.findAll();
    for (final RoleModelRelationEntity rm : roleModelItems) {
      fillPermissionByGroup(permissionByGroup, rm);
      fillPermissionByRole(rolesByPermission, rm);
    }
    return RoleModelRaw.builder()
        .permissionByGroup(permissionByGroup)
        .rolesByPermission(rolesByPermission)
        .build();
  }

  private void fillPermissionByGroup(
      final Map<String, PermissionGroupPermission> permissionByGroup,
      final RoleModelRelationEntity rm) {
    final PermissionGroupPermission groupPermission =
        permissionByGroup.get(rm.getPermissionGroup().getName());
    if (isNull(groupPermission)) {
      final PermissionGroupPermission groupPermissionNew = new PermissionGroupPermission();
      groupPermissionNew.setGroupId(rm.getPermissionGroup().getId());
      groupPermissionNew.setGroupName(rm.getPermissionGroup().getDescription());
      groupPermissionNew.setSystemName(rm.getPermissionGroup().getName());

      final Set<PermissionEntity> permissions = new HashSet<>();
      permissions.add(rm.getPermission());

      groupPermissionNew.setPermission(
          permissions.stream()
              .map(item -> mapper.map(item, Permission.class))
              .collect(Collectors.toSet()));
      permissionByGroup.put(rm.getPermissionGroup().getName(), groupPermissionNew);
    } else {
      groupPermission.getPermission().add(mapper.map(rm.getPermission(), Permission.class));
      permissionByGroup.put(rm.getPermissionGroup().getName(), groupPermission);
    }
  }

  private void fillPermissionByRole(
      final Map<String, PermissionRoleGroup> rolesByPermission, final RoleModelRelationEntity rm) {
    final PermissionRoleGroup permissionRole =
        rolesByPermission.get(rm.getPermission().getSystemName());
    if (isNull(permissionRole)) {
      final PermissionRoleGroup permissionRoleNew = new PermissionRoleGroup();

      final Set<RoleEntity> roles = new HashSet<>();
      roles.add(rm.getRole());
      permissionRoleNew.setRoles(
          roles.stream().map(item -> mapper.map(item, Role.class)).collect(Collectors.toSet()));
      rolesByPermission.put(rm.getPermission().getSystemName(), permissionRoleNew);
    } else {
      permissionRole.getRoles().add(mapper.map(rm.getRole(), Role.class));
      rolesByPermission.put(rm.getPermission().getSystemName(), permissionRole);
    }
  }
}
