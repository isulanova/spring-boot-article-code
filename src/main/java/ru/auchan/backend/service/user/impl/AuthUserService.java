package ru.auchan.backend.service.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.role.shared.request.view.RoleItem;
import ru.auchan.backend.controller.role.shared.response.RoleSimpleItemResponse;
import ru.auchan.backend.controller.user.shared.request.AuthUserUpdateRequest;
import ru.auchan.backend.controller.user.shared.request.UserCreateRequest;
import ru.auchan.backend.controller.user.shared.response.AuthUserItemWithRolesResponse;
import ru.auchan.backend.controller.user.shared.response.UpdatedUserRoleServiceResponse;
import ru.auchan.backend.io.entity.AuthUserEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;
import ru.auchan.backend.io.projections.AuthUserWithRolesProj;
import ru.auchan.backend.io.repository.AuthUserRepo;
import ru.auchan.backend.io.repository.RoleRepo;
import ru.auchan.backend.service.role.exception.RoleNotFoundException;
import ru.auchan.backend.service.user.IAuthUserService;
import ru.auchan.backend.service.user.exception.UserAlreadyExistsException;
import ru.auchan.backend.service.user.exception.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserService implements IAuthUserService {

  private final AuthUserRepo userRepo;
  private final RoleRepo roleRepo;
  private final ModelMapper mapper;

  @Override
  public Optional<AuthUserItemWithRolesResponse> findByKeycloakId(UUID uuid) {
    final Optional<AuthUserWithRolesProj> projOptional = userRepo.findByKeycloakId(uuid);
    if (projOptional.isPresent()) {
      return Optional.of(AuthUserItemWithRolesResponse.fromProjection(projOptional.get()));
    } else {
      log.warn("User with keycloak ID: {} not found", uuid);
      return Optional.empty();
    }
  }

  @Override
  public List<AuthUserItemWithRolesResponse> findByKeycloakIdList(List<UUID> uuidList) {
    final List<AuthUserWithRolesProj> projList = userRepo.findByKeycloakIdList(uuidList);
    if (projList.isEmpty()) {
      return Collections.emptyList();
    } else {
      return projList.stream().map(AuthUserItemWithRolesResponse::fromProjection).toList();
    }
  }

  @Override
  public Optional<AuthUserItemWithRolesResponse> addUser(UserCreateRequest userCreateRequest) {
    final Optional<AuthUserEntity> authUserFromDb =
        userRepo.findById(userCreateRequest.getKeycloakId());
    if (authUserFromDb.isPresent()) {
      log.error(
          "Create failed. User with Keycloak ID: {} already exists",
          userCreateRequest.getKeycloakId());
      throw new UserAlreadyExistsException(userCreateRequest.getKeycloakId());
    } else {
      final AuthUserEntity persistentEntity = new AuthUserEntity();
      persistentEntity.setUserName(userCreateRequest.getUserName());
      persistentEntity.setKeycloakId(userCreateRequest.getKeycloakId());
      final List<UUID> roleIds = new ArrayList<>(userCreateRequest.getUserRoles());

      persistentEntity.setUserRoles(new HashSet<>(roleRepo.findAllById(roleIds)));
      userRepo.save(persistentEntity);
      log.info(
          "User with params {} created success.Record ID: {}",
          userCreateRequest,
          userCreateRequest.getKeycloakId());
      return findByKeycloakId(userCreateRequest.getKeycloakId());
    }
  }

  @Override
  public boolean removeByKeycloakId(UUID id) {
    log.warn("Attempt to delete user with keycloak id {}", id);
    return logDeleteAction(removeUser(userRepo.findById(id)), id);
  }

  @Override
  public Page<AuthUserItemWithRolesResponse> findByKeycloakUserName(
      String userName, Pageable pageable) {
    return userRepo
        .findByUserName(userName, pageable)
        .map(AuthUserItemWithRolesResponse::fromProjection);
  }

  @Override
  public Page<AuthUserItemWithRolesResponse> findUsersByRoleId(UUID roleId, Pageable pageable) {
    return userRepo
        .findByUserRole(roleId, pageable)
        .map(AuthUserItemWithRolesResponse::fromProjection);
  }

  @Override
  public Set<UUID> findUsersByRoleId(List<UUID> roleIdList) {
    return userRepo.findUserIdListByRoleId(roleIdList);
  }

  @Override
  public Set<UUID> findUsersByRoleNameList(List<String> roleNames) {
    return userRepo.findUserIdListByRoleNames(roleNames);
  }

  @Override
  public Page<AuthUserItemWithRolesResponse> list(Pageable pageable) {
    return userRepo.list(pageable).map(AuthUserItemWithRolesResponse::fromProjection);
  }

  @Override
  public Optional<UpdatedUserRoleServiceResponse> updateUser(
      UUID userId, AuthUserUpdateRequest updateRequest) {
    final Optional<AuthUserEntity> authUserFromDb = userRepo.findById(userId);
    if (authUserFromDb.isEmpty()) {
      log.error("Update failed.User with Keycloak ID: {} not found", updateRequest.getKeycloakId());
      throw new UserNotFoundException(userId);
    } else {
      final AuthUserEntity persistentEntity = authUserFromDb.get();
      persistentEntity.setUserName(updateRequest.getUserName());
      final List<UUID> roleIds = new ArrayList<>(updateRequest.getUserRoles());

      persistentEntity.setUserRoles(new HashSet<>(roleRepo.findAllById(roleIds)));
      final AuthUserEntity updatedEntity = userRepo.save(persistentEntity);

      final UpdatedUserRoleServiceResponse response = new UpdatedUserRoleServiceResponse();
      response.setKeycloakId(userId);
      response.setUserName(updateRequest.getUserName());
      response.setCurrentRoles(
          setRoles(persistentEntity).stream()
              .map(item -> mapper.map(item, RoleSimpleItemResponse.class))
              .collect(Collectors.toSet()));
      response.setModifiedRoles(
          setRoles(updatedEntity).stream()
              .map(item -> mapper.map(item, RoleSimpleItemResponse.class))
              .collect(Collectors.toSet()));

      return Optional.of(response);
    }
  }

  @Override
  public boolean addRoleToUser(UUID userKeycloakId, String roleSystemName) {
    final var userOptional = userRepo.findById(userKeycloakId);
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException(userKeycloakId);
    } else {
      final var roleOptional = roleRepo.findByName(roleSystemName);
      if (roleOptional.isPresent()) {
        final var user = userOptional.get();
        final var userRoles = new HashSet<>(user.getUserRoles());
        userRoles.add(roleOptional.get());
        user.setUserRoles(userRoles);
        userRepo.save(user);
        return true;
      } else {
        throw new RoleNotFoundException(roleSystemName);
      }
    }
  }

  @Override
  public boolean removeRoleFromUser(UUID userKeycloakId, String roleSystemName) {
    final var userOptional = userRepo.findById(userKeycloakId);
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException(userKeycloakId);
    } else {
      final var user = userOptional.get();
      user.setUserRoles(
          user.getUserRoles().stream()
              .filter(role -> !role.getName().equalsIgnoreCase(roleSystemName))
              .collect(Collectors.toSet()));
      userRepo.save(user);
      return true;
    }
  }

  private Set<RoleItem> setRoles(final AuthUserEntity entity) {
    final Set<RoleItem> currentRoles = new HashSet<>();
    for (final RoleEntity role : entity.getUserRoles()) {
      final RoleItem itemBase = new RoleItem();
      itemBase.setRoleId(role.getId());
      itemBase.setName(role.getName());
      itemBase.setDescription(role.getDescription());
      itemBase.setLabel(role.getLabel());
      currentRoles.add(itemBase);
    }
    return currentRoles;
  }

  private boolean removeUser(final Optional<AuthUserEntity> userFromDb) {
    if (userFromDb.isEmpty()) {
      return false;
    }
    userRepo.delete(userFromDb.get());
    return !userRepo.existsById(userFromDb.get().getKeycloakId());
  }

  private boolean logDeleteAction(final boolean deleteResult, final UUID id) {
    if (deleteResult) {
      log.info("User with id: {} remove success", id);
    } else {
      log.error("User with id: {} remove fail", id);
    }
    return deleteResult;
  }
}
