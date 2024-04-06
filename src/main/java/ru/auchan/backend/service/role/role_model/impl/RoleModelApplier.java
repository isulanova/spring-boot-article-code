package ru.auchan.backend.service.role.role_model.impl;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystem;
import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystemGroup;
import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystemRole;
import ru.auchan.backend.io.entity.PermissionGroupEntity;
import ru.auchan.backend.io.entity.relations.RoleModelRelationEntity;
import ru.auchan.backend.io.repository.RoleModelRepo;
import ru.auchan.backend.service.permission.IPermissionGroupService;
import ru.auchan.backend.service.permission.IPermissionService;
import ru.auchan.backend.service.permission.model.Permission;
import ru.auchan.backend.service.permission.model.PermissionGroup;
import ru.auchan.backend.service.role.IRoleService;
import ru.auchan.backend.service.role.exception.ApplyRoleModelException;
import ru.auchan.backend.service.role.exception.RoleModelException;
import ru.auchan.backend.service.role.model.Role;
import ru.auchan.backend.service.role.model.RoleModel;
import ru.auchan.backend.service.role.role_model.IRoleModelApplier;
import ru.auchan.backend.service.role.role_model.model.PermissionRoleGroup;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleModelApplier implements IRoleModelApplier {

  private final RoleModelRepo roleModelRepo;

  private final IRoleService roleService;
  private final IPermissionGroupService permissionGroupService;
  private final IPermissionService permissionService;
  private final ModelMapper mapper;

  @Override
  public void applyRoleModel(final RoleModelSystem roleModelSystem) {
    validateApplyRequest(roleModelSystem);

    for (final RoleModelSystemGroup group : roleModelSystem.getGroup()) {
      updatePermissionsByGroup(group);
    }
  }

  private void updatePermissionsByGroup(final RoleModelSystemGroup group) {
    final List<RoleModel> appliedRoleModelByGroup = createRoleModelStructure(group);
    log.info(
        "Try to update {} relations for group {}",
        appliedRoleModelByGroup.size(),
        group.getSystemName());
    final List<RoleModelRelationEntity> currentRoleModelByGroup =
        roleModelRepo.findByPermissionGroup(getPermissionGroup(group.getSystemName()));

    final List<RoleModel> appliedRoleModelItems = new ArrayList<>();
    for (final RoleModel updatedRoleModelItem : appliedRoleModelByGroup) {
      final Optional<RoleModel> elementFromCurrentRoleModel =
          foundElement(
              currentRoleModelByGroup.stream()
                  .map(item -> mapper.map(item, RoleModel.class))
                  .collect(Collectors.toList()),
              updatedRoleModelItem);
      if (!elementFromCurrentRoleModel.isPresent()) {
        appliedRoleModelItems.add(updatedRoleModelItem);
      } else {
        currentRoleModelByGroup.remove(elementFromCurrentRoleModel.get());
      }
    }

    if (!currentRoleModelByGroup.isEmpty()) {
      log.warn("Clear {} elements for current role model", currentRoleModelByGroup.size());
      roleModelRepo.deleteAll(currentRoleModelByGroup);
    }

    if (appliedRoleModelItems.isEmpty()) {
      log.info("No new elements found.Update skipped");
    } else {
      log.info("Found {} new elements.Update role model", appliedRoleModelItems.size());
      roleModelRepo.saveAll(
          appliedRoleModelItems.stream()
              .map(item -> mapper.map(item, RoleModelRelationEntity.class))
              .collect(Collectors.toSet()));
    }
  }

  private Optional<RoleModel> foundElement(
      final List<RoleModel> currentRoleModelByGroup, final RoleModel updatedRoleModelItem) {
    return currentRoleModelByGroup.stream()
        .filter(
            item ->
                item.getRole().equals(updatedRoleModelItem.getRole())
                    && item.getPermissionGroup().equals(updatedRoleModelItem.getPermissionGroup())
                    && item.getPermission().equals(updatedRoleModelItem.getPermission()))
        .findFirst();
  }

  private List<RoleModel> createRoleModelStructure(final RoleModelSystemGroup group) {
    final var permissionGroup = getPermissionGroup(group.getSystemName());
    final List<PermissionRoleGroup> permissionRoleList = new ArrayList<>();
    group
        .getPermissions()
        .forEach(
            p -> {
              final PermissionRoleGroup permissionRole = new PermissionRoleGroup();
              permissionRole.setPermission(getPermission(p.getSystemName()));
              permissionRole.setPermissionGroup(mapper.map(permissionGroup, PermissionGroup.class));
              permissionRole.setRoles(
                  getRolesBySystemNamesSet(
                      p.getRoles().stream()
                          .map(RoleModelSystemRole::getSystemName)
                          .collect(Collectors.toSet())));
              permissionRoleList.add(permissionRole);
            });
    return createRoleModelItems(permissionRoleList);
  }

  private List<RoleModel> createRoleModelItems(final List<PermissionRoleGroup> permissionRoleList) {
    if (permissionRoleList.isEmpty()) {
      return Collections.emptyList();
    }
    return permissionRoleList.stream()
        .map(this::createRoleModelItemsFromPermissionRole)
        .flatMap(List::stream)
        .toList();
  }

  private List<RoleModel> createRoleModelItemsFromPermissionRole(
      final PermissionRoleGroup permissionRole) {
    final List<RoleModel> roleModelItemsList = new ArrayList<>();
    if (permissionRole.getRoles().isEmpty()) {
      final RoleModel roleModel = new RoleModel();
      roleModel.setPermission(permissionRole.getPermission());
      roleModel.setPermissionGroup(permissionRole.getPermissionGroup());
      roleModelItemsList.add(roleModel);
    } else {
      for (final Role role : permissionRole.getRoles()) {
        final RoleModel roleModel = new RoleModel();
        roleModel.setRole(role);
        roleModel.setPermission(permissionRole.getPermission());
        roleModel.setPermissionGroup(permissionRole.getPermissionGroup());
        roleModelItemsList.add(roleModel);
      }
    }
    return roleModelItemsList;
  }

  private void validateApplyRequest(final RoleModelSystem roleModelSystem) {
    if (isNull(roleModelSystem)
        || isNull(roleModelSystem.getGroup())
        || roleModelSystem.getGroup().isEmpty()) {
      throw new ApplyRoleModelException("Error apply role model from state.Structure is invalid");
    }
  }

  private Set<Role> getRolesBySystemNamesSet(final Set<String> roleNames) {
    if (isNull(roleNames) || roleNames.isEmpty()) {
      return Collections.emptySet();
    }
    return roleService.findBySystemNameIn(roleNames);
  }

  private Permission getPermission(final String permissionName) {
    if (isNull(permissionName)) {
      final var message = "Permission name is null";
      log.error(message);
      throw new RoleModelException(message);
    } else {
      final var permission = permissionService.findBySystemName(permissionName);
      if (permission.isEmpty()) {
        final var message = String.format("Permissions with name: %s not found", permissionName);
        log.error(message);
        throw new RoleModelException(message);
      } else {
        return permission.get();
      }
    }
  }

  private PermissionGroupEntity getPermissionGroup(final String systemName) {
    if (isNull(systemName)) {
      final var message = "Permission group name is null";
      log.error(message);
      throw new RoleModelException(message);
    } else {
      final var permissionGroup = permissionGroupService.findBySystemNameDb(systemName);
      if (permissionGroup.isEmpty()) {
        final var message = String.format("Permission group with name %s not found", systemName);
        log.error(message);
        throw new RoleModelException(message);
      } else {
        return permissionGroup.get();
      }
    }
  }
}
