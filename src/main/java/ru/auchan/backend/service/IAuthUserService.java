package ru.auchan.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.auchan.backend.controller.shared.request.user.AuthUserUpdateRequest;
import ru.auchan.backend.controller.shared.request.user.UserCreateRequest;
import ru.auchan.backend.controller.shared.response.user.AuthUserItemWithRolesResponse;
import ru.auchan.backend.controller.shared.response.user.UpdatedUserRoleServiceResponse;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface IAuthUserService {

  Optional<AuthUserItemWithRolesResponse> findByKeycloakId(UUID uuid);

  List<AuthUserItemWithRolesResponse> findByKeycloakIdList(List<UUID> uuidList);

  Optional<AuthUserItemWithRolesResponse> addUser(UserCreateRequest userCreateRequest);

  Optional<AuthUserItemWithRolesResponse> addSupplierCandidateUser(
      UserCreateRequest userCreateRequest);

  boolean removeByKeycloakId(UUID keycloakId);

  Page<AuthUserItemWithRolesResponse> findByKeycloakUserName(String userName, Pageable pageable);

  Page<AuthUserItemWithRolesResponse> findUsersByRoleId(UUID roleId, Pageable pageable);

  Set<UUID> findUsersByRoleId(List<UUID> roleIdList);

  Set<UUID> findUsersByRoleNameList(List<String> roleNames);

  Page<AuthUserItemWithRolesResponse> list(Pageable pageable);

  Optional<UpdatedUserRoleServiceResponse> updateUser(
      UUID userId, AuthUserUpdateRequest updateRequest);

  boolean addRoleToUser(UUID userKeycloakId, String roleSystemName);

  boolean removeRoleFromUser(UUID userKeycloakId, String roleSystemName);
}
