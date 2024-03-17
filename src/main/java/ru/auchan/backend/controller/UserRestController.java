package ru.auchan.backend.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.metadata.UserRestControllerMetadata;
import ru.auchan.backend.controller.shared.ListWrapper;
import ru.auchan.backend.controller.shared.request.user.AuthUserUpdateRequest;
import ru.auchan.backend.controller.shared.request.user.UserCreateRequest;
import ru.auchan.backend.controller.shared.response.user.AuthUserItemWithRolesResponse;
import ru.auchan.backend.controller.shared.response.user.UpdatedUserRoleServiceResponse;
import ru.auchan.backend.service.IAuthUserService;
import ru.auchan.backend.service.IUIService;

import static ru.auchan.backend.controller.util.RestControllerUtil.createResponseMap;

@RestController
@RequiredArgsConstructor
public class UserRestController implements UserRestControllerMetadata {

  private final IAuthUserService userService;
  private final IUIService uiAccessService;

  @Override
  public ResponseEntity<Set<UUID>> findUsersByRoleIds(final List<UUID> roleIdList) {
    return ResponseEntity.ok(userService.findUsersByRoleId(roleIdList));
  }

  @Override
  public ResponseEntity<Set<UUID>> findUsersByRoleSystemNames(final List<String> roleNameList) {
    return ResponseEntity.ok(userService.findUsersByRoleNameList(roleNameList));
  }

  public ResponseEntity<AuthUserItemWithRolesResponse> findByKeycloakId(final UUID id) {
    return createFindUserResponse(userService.findByKeycloakId(id));
  }

  public ResponseEntity<List<AuthUserItemWithRolesResponse>> findByKeycloakIdList(
      @RequestBody final ListWrapper<UUID> uuidList) {
    if (uuidList.getPayload().isEmpty()) {
      return ResponseEntity.ok(Collections.emptyList());
    }
    return ResponseEntity.ok(userService.findByKeycloakIdList(uuidList.getPayload()));
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

  public ResponseEntity<Map<String, Object>> findByRole(
      final UUID id, final int page, final int size) {
    try {
      final Pageable paging = PageRequest.of(page, size);
      final Page<AuthUserItemWithRolesResponse> dataPage =
          userService.findUsersByRoleId(id, paging);
      return new ResponseEntity<>(createResponseMap(dataPage), HttpStatus.OK);
    } catch (final Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  public ResponseEntity<Map<String, Object>> findByUserName(
      final String username, final int page, final int size) {
    try {
      final Pageable paging = PageRequest.of(page, size);
      final Page<AuthUserItemWithRolesResponse> dataPage =
          userService.findByKeycloakUserName(username, paging);
      return new ResponseEntity<>(createResponseMap(dataPage), HttpStatus.OK);
    } catch (final Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  public ResponseEntity<Boolean> deleteByKeycloakId(final UUID id) {
    return removeUser(userService.removeByKeycloakId(id));
  }

  private ResponseEntity<Boolean> removeUser(final boolean deleteResult) {
    if (deleteResult) {
      return new ResponseEntity<>(Boolean.TRUE, HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND);
    }
  }

  private ResponseEntity<AuthUserItemWithRolesResponse> createFindUserResponse(
      final Optional<AuthUserItemWithRolesResponse> userResponse) {
    return userResponse
        .map(authUserResponse -> ResponseEntity.status(HttpStatus.OK).body(authUserResponse))
        .orElse(null);
  }

  @Override
  public ResponseEntity<Boolean> addRoleToUser(
      final UUID userKeycloakId, final String roleSystemName) {
    return ResponseEntity.ok(userService.addRoleToUser(userKeycloakId, roleSystemName));
  }

  @Override
  public ResponseEntity<Boolean> removeRoleFromUser(
      final UUID userKeycloakId, final String roleSystemName) {
    return ResponseEntity.ok(userService.removeRoleFromUser(userKeycloakId, roleSystemName));
  }
}
