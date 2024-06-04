package ru.auchan.backend.controller.user;

import static ru.auchan.backend.controller.util.RestControllerUtil.createResponseMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.user.metadata.UserRestControllerMetadata;
import ru.auchan.backend.controller.user.shared.request.AuthUserUpdateRequest;
import ru.auchan.backend.controller.user.shared.request.UserCreateRequest;
import ru.auchan.backend.controller.user.shared.response.AuthUserItemWithRolesResponse;
import ru.auchan.backend.controller.user.shared.response.UpdatedUserRoleServiceResponse;
import ru.auchan.backend.controller.util.ListWrapper;
import ru.auchan.backend.service.access.IUIService;
import ru.auchan.backend.service.user.IAuthUserService;

@RestController
@RequiredArgsConstructor
public class UserRestController implements UserRestControllerMetadata {

  private final IAuthUserService userService;
  private final IUIService uiAccessService;

  @Override
  public Set<UUID> findUsersByRoleIds(final List<UUID> roleIdList) {
    return userService.findUsersByRoleId(roleIdList);
  }

  @Override
  public Set<UUID> findUsersByRoleSystemNames(final List<String> roleNameList) {
    return userService.findUsersByRoleNameList(roleNameList);
  }

  public AuthUserItemWithRolesResponse findByKeycloakId(final UUID id) {
    return userService.findByKeycloakId(id).orElse(null);
  }

  public List<AuthUserItemWithRolesResponse> findByKeycloakIdList(
      final ListWrapper<UUID> uuidList) {
    if (uuidList.getPayload().isEmpty()) {
      return Collections.emptyList();
    }
    return userService.findByKeycloakIdList(uuidList.getPayload());
  }

  public ResponseEntity<AuthUserItemWithRolesResponse> addUser(
      final UserCreateRequest userCreateRequest) {
    return userService
        .addUser(userCreateRequest)
        .map(authUserResponse -> ResponseEntity.status(HttpStatus.CREATED).body(authUserResponse))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  public ResponseEntity<UpdatedUserRoleServiceResponse> update(
      final UUID id, final AuthUserUpdateRequest updateRequest) {
    final Optional<UpdatedUserRoleServiceResponse> updated =
        userService.updateUser(id, updateRequest);
    uiAccessService.renewAccessMap(id);
    return updated
        .map(authUserResponse -> ResponseEntity.status(HttpStatus.ACCEPTED).body(authUserResponse))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  public Map<String, Object> findByRole(final UUID id, final int page, final int size) {
    try {
      final Pageable paging = PageRequest.of(page, size);
      final Page<AuthUserItemWithRolesResponse> dataPage =
          userService.findUsersByRoleId(id, paging);
      return createResponseMap(dataPage);
    } catch (final Exception e) {
      return Collections.emptyMap();
    }
  }

  public Map<String, Object> findByUserName(final String username, final int page, final int size) {
    try {
      final Pageable paging = PageRequest.of(page, size);
      final Page<AuthUserItemWithRolesResponse> dataPage =
          userService.findByKeycloakUserName(username, paging);
      return createResponseMap(dataPage);
    } catch (final Exception e) {
      return Collections.emptyMap();
    }
  }

  public Boolean deleteByKeycloakId(final UUID id) {
    return userService.removeByKeycloakId(id);
  }

  @Override
  public Boolean addRoleToUser(final UUID userKeycloakId, final String roleSystemName) {
    return userService.addRoleToUser(userKeycloakId, roleSystemName);
  }

  @Override
  public Boolean removeRoleFromUser(final UUID userKeycloakId, final String roleSystemName) {
    return userService.removeRoleFromUser(userKeycloakId, roleSystemName);
  }
}
