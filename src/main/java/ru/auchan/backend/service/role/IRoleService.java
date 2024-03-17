package ru.auchan.backend.service.role;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import ru.auchan.backend.controller.shared.request.role.RoleItemRequest;
import ru.auchan.backend.controller.shared.response.role.RoleItemAdminResponse;
import ru.auchan.backend.controller.shared.response.role.RoleItemBySystemNameResponse;
import ru.auchan.backend.controller.shared.response.role.RoleItemResponse;
import ru.auchan.backend.controller.shared.response.role.RoleWithPermissionsItemResponse;
import ru.auchan.backend.controller.shared.response.role.RolesByUserResponse;
import ru.auchan.backend.model.Role;

public interface IRoleService {

  List<RoleItemAdminResponse> findRoleItemResponseData();

  List<RoleItemAdminResponse> findRoleItemResponseDataFromCache();

  Optional<RolesByUserResponse> getRoleListByKeycloakId(UUID keycloakId);

  Optional<Role> findById(UUID id);

  Optional<RoleItemResponse> findByIdentifier(UUID uuid);

  Optional<RoleItemResponse> addRole(RoleItemRequest roleCreationRequest);

  boolean removeRole(UUID id);

  Optional<RoleItemResponse> updateRole(UUID id, RoleItemRequest itemRequest);

  Optional<RoleWithPermissionsItemResponse> getRoleWithPermission(UUID id);

  List<Role> findAllById(List<UUID> uuids);

  Optional<RoleWithPermissionsItemResponse> findBySystemName(String systemName);

  Set<Role> findBySystemNameIn(Set<String> systemNames);
}
