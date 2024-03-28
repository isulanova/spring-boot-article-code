package ru.auchan.backend.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.shared.request.role.RoleItemRequest;
import ru.auchan.backend.controller.permission.shared.response.PermissionItemResponse;
import ru.auchan.backend.controller.shared.response.role.RoleItemAdminResponse;
import ru.auchan.backend.controller.shared.response.role.RoleItemResponse;
import ru.auchan.backend.controller.shared.response.role.RoleWithPermissionsItemResponse;
import ru.auchan.backend.controller.shared.response.role.RolesByUserResponse;
import ru.auchan.backend.io.entity.relations.RoleModelRelationEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;
import ru.auchan.backend.io.projection.RoleByKeycloakIdProj;
import ru.auchan.backend.io.projection.RoleProj;
import ru.auchan.backend.io.repository.RoleRepo;
import ru.auchan.backend.model.Role;
import ru.auchan.backend.service.exception.RoleAlreadyExistsException;
import ru.auchan.backend.service.exception.RoleNotFoundException;
import ru.auchan.backend.service.role.IRoleService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

  private final RoleRepo roleRepo;
  private final ModelMapper mapper;

  @Override
  public List<RoleItemAdminResponse> findRoleItemResponseData() {
    return findRoleItemResponseDataFromCache();
  }

  @Override
  @Cacheable(value = "role_group_list")
  public List<RoleItemAdminResponse> findRoleItemResponseDataFromCache() {
    return getRoleList();
  }

  private List<RoleItemAdminResponse> getRoleList() {
    return roleRepo.getRoleListProj().stream()
        .map(RoleItemAdminResponse::fromProjection)
        .sorted()
        .toList();
  }

  @Override
  @Cacheable(value = "roles_by_keycloak_id", key = "{#keycloakId}", condition = "#keycloakId!=null")
  public Optional<RolesByUserResponse> getRoleListByKeycloakId(final UUID keycloakId) {
    log.info("Try to find role list by user with id: {}", keycloakId);

    final Optional<RoleByKeycloakIdProj> roleByKeycloakIdProj =
        roleRepo.findByKeycloakId(keycloakId);
    if (roleByKeycloakIdProj.isEmpty()) {
      log.error("Roles by user {} not found", keycloakId);
      return Optional.empty();
    }

    final RolesByUserResponse response =
        RolesByUserResponse.fromProjection(roleByKeycloakIdProj.get());
    log.info("Role list by user {} generated success", keycloakId);
    return Optional.of(response);
  }

  @Override
  public Optional<RoleItemResponse> findByIdentifier(final UUID uuid) {
    return roleRepo.findByIdentifier(uuid).map(RoleItemResponse::fromProjection);
  }

  @Override
  public Optional<Role> findById(final UUID uuid) {
    return roleRepo.findById(uuid).map(item -> mapper.map(item, Role.class));
  }

  @Override
  public Optional<RoleWithPermissionsItemResponse> findBySystemName(final String systemName) {
    final Optional<RoleEntity> roleFromDb = roleRepo.findBySystemName(systemName);
    if (roleFromDb.isPresent()) {
      return Optional.of(mapper.map(roleFromDb.get(), RoleWithPermissionsItemResponse.class));
    } else {
      log.error("Role with systemName: {} not found", systemName);
      throw new RoleNotFoundException(systemName);
    }
  }

  @Override
  public Set<Role> findBySystemNameIn(final Set<String> systemNames) {
    return roleRepo.findBySystemNameIn(systemNames).stream()
        .map(item -> mapper.map(item, Role.class))
        .collect(Collectors.toSet());
  }

  @Override
  public Optional<RoleItemResponse> addRole(final RoleItemRequest roleCreationRequest) {
    log.info("Trying to create role with params: {}", roleCreationRequest);
    final Optional<RoleEntity> roleFromDb =
        roleRepo.findBySystemName(roleCreationRequest.getSystemName());
    if (roleFromDb.isPresent()) {
      log.error("Role: {} already exists", roleCreationRequest.getSystemName());
      throw new RoleAlreadyExistsException(roleCreationRequest.getSystemName());
    }
    final RoleEntity persistentEntity =
        roleRepo.save(
            mapper.map(
                new Role(
                    roleCreationRequest.getSystemName(),
                    roleCreationRequest.getUiName(),
                    roleCreationRequest.getDescription()),
                RoleEntity.class));

    final Optional<RoleItemResponse> result = findByIdentifier(persistentEntity.getId());
    logRoleCreationAction(result, roleCreationRequest);
    return result;
  }

  @Override
  public Optional<RoleItemResponse> updateRole(final UUID id, final RoleItemRequest itemRequest) {
    log.info("Trying update role: {}", id);
    final Optional<RoleEntity> roleFromDbOptional = roleRepo.findById(id);
    if (roleFromDbOptional.isEmpty()) {
      log.error("Role: {} not found", id);
      throw new RoleNotFoundException(id);
    } else {
      final RoleEntity roleFromDb = roleFromDbOptional.get();
      roleFromDb.setDescription(itemRequest.getDescription());
      roleFromDb.setSystemName(itemRequest.getSystemName());
      roleFromDb.setUiName(itemRequest.getUiName());
      final RoleEntity updatedEntity = roleRepo.save(roleFromDb);
      return findByIdentifier(updatedEntity.getId());
    }
  }

  @Override
  public Optional<RoleWithPermissionsItemResponse> getRoleWithPermission(final UUID id) {
    final Optional<RoleEntity> roleFromDbOptional = roleRepo.findById(id);
    if (roleFromDbOptional.isPresent()) {
      final RoleEntity roleFromDb = roleFromDbOptional.get();
      final Set<PermissionItemResponse> permissions =
          roleFromDb.getModel().stream()
              .map(RoleModelRelationEntity::getPermission)
              .map(PermissionItemResponse::fromEntity)
              .collect(Collectors.toSet());
      final RoleWithPermissionsItemResponse response =
          RoleWithPermissionsItemResponse.roleItemWithPermissionResponseBuilder()
              .description(roleFromDb.getDescription())
              .id(roleFromDb.getId())
              .systemName(roleFromDb.getSystemName())
              .uiName(roleFromDb.getUiName())
              .permissions(permissions)
              .build();
      return Optional.of(response);
    } else {
      log.error("Role with ID: {} not found", id);
      throw new RoleNotFoundException(id);
    }
  }

  @Override
  public boolean removeRole(final UUID id) {
    final Optional<RoleProj> roleFromDb = roleRepo.findByIdentifier(id);
    if (roleFromDb.isEmpty()) {
      throw new EntityNotFoundException("Record already deleted or not found");
    }
    roleRepo.deleteById(id);
    return !roleRepo.existsById(id);
  }

  @Override
  public List<Role> findAllById(final List<UUID> uuids) {
    return roleRepo.findAllById(uuids).stream().map(item -> mapper.map(item, Role.class)).toList();
  }

  private void logRoleCreationAction(
      final Optional<RoleItemResponse> result, final RoleItemRequest roleCreationRequest) {
    if (result.isPresent()) {
      log.info(
          "Role: {} created success. ID: {}", result.get().getName(), result.get().getRoleId());
    } else {
      log.error("Role: {} created error.", roleCreationRequest.getSystemName());
    }
  }
}
