package ru.auchan.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.shared.request.user.AuthUserUpdateRequest;
import ru.auchan.backend.controller.shared.request.user.UserCreateRequest;
import ru.auchan.backend.controller.shared.response.user.AuthUserItemWithRolesResponse;
import ru.auchan.backend.controller.shared.response.user.UpdatedUserRoleServiceResponse;

@Slf4j
@Service
public class AuthUserService implements IAuthUserService {
  @Override
  public Optional<AuthUserItemWithRolesResponse> findByKeycloakId(UUID uuid) {
    return Optional.empty();
  }

  @Override
  public List<AuthUserItemWithRolesResponse> findByKeycloakIdList(List<UUID> uuidList) {
    return null;
  }

  @Override
  public Optional<AuthUserItemWithRolesResponse> addUser(UserCreateRequest userCreateRequest) {
    return Optional.empty();
  }

  @Override
  public Optional<AuthUserItemWithRolesResponse> addSupplierCandidateUser(
      UserCreateRequest userCreateRequest) {
    return Optional.empty();
  }

  @Override
  public boolean removeByKeycloakId(UUID keycloakId) {
    return false;
  }

  @Override
  public Page<AuthUserItemWithRolesResponse> findByKeycloakUserName(
      String userName, Pageable pageable) {
    return null;
  }

  @Override
  public Page<AuthUserItemWithRolesResponse> findUsersByRoleId(UUID roleId, Pageable pageable) {
    return null;
  }

  @Override
  public Set<UUID> findUsersByRoleId(List<UUID> roleIdList) {
    return null;
  }

  @Override
  public Set<UUID> findUsersByRoleNameList(List<String> roleNames) {
    return null;
  }

  @Override
  public Page<AuthUserItemWithRolesResponse> list(Pageable pageable) {
    return null;
  }

  @Override
  public Optional<UpdatedUserRoleServiceResponse> updateUser(
      UUID userId, AuthUserUpdateRequest updateRequest) {
    return Optional.empty();
  }

  @Override
  public boolean addRoleToUser(UUID userKeycloakId, String roleSystemName) {
    return false;
  }

  @Override
  public boolean removeRoleFromUser(UUID userKeycloakId, String roleSystemName) {
    return false;
  }
}
