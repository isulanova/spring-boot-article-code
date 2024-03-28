package ru.auchan.backend.controller.permission;

import static java.util.Objects.isNull;
import static ru.auchan.backend.controller.util.RestControllerUtil.createResponseMap;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.permission.metadata.PermissionControllerMetadata;
import ru.auchan.backend.controller.permission.shared.request.PermissionItemRequest;
import ru.auchan.backend.controller.permission.shared.response.PermissionItemResponse;
import ru.auchan.backend.controller.util.ListWrapper;
import ru.auchan.backend.service.permission.IPermissionService;

@RestController
@RequiredArgsConstructor
public class PermissionController implements PermissionControllerMetadata {

  private final IPermissionService permissionService;
  private final ModelMapper mapper;

  public ResponseEntity<Map<String, Object>> getPermissionListPageable(
      final int page, final int size) {
    try {
      final Pageable paging = PageRequest.of(page, size);
      final Page<PermissionItemResponse> dataPage = permissionService.list(paging);
      return new ResponseEntity<>(createResponseMap(dataPage), HttpStatus.OK);
    } catch (final Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  public ResponseEntity<Set<PermissionItemResponse>> getPermissionList() {
    return ResponseEntity.ok(
        permissionService.list().stream()
            .map(item -> mapper.map(item, PermissionItemResponse.class))
            .collect(Collectors.toSet()));
  }

  @Override
  public ResponseEntity<Set<PermissionItemResponse>> getPermissionListByPermissionGroupList(
      final ListWrapper<String> groupList) {
    if (isNull(groupList.getPayload()) || groupList.getPayload().isEmpty()) {
      return ResponseEntity.ok(Collections.emptySet());
    }
    return ResponseEntity.ok(
        permissionService.getPermissionListByPermissionGroupList(groupList.getPayload()).stream()
            .map(item -> mapper.map(item, PermissionItemResponse.class))
            .collect(Collectors.toSet()));
  }

  public ResponseEntity<PermissionItemResponse> getById(final UUID id) {
    final Optional<PermissionItemResponse> response = permissionService.findById(id);
    return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  public ResponseEntity<PermissionItemResponse> create(final PermissionItemRequest itemRequest) {
    final Optional<PermissionItemResponse> permissionProj =
        permissionService.addPermission(itemRequest);
    return permissionProj
        .map(proj -> ResponseEntity.status(HttpStatus.CREATED).body(proj))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  public ResponseEntity<Void> deletePermission(final UUID id) {
    permissionService.removePermission(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  public ResponseEntity<PermissionItemResponse> update(
      final UUID id, final PermissionItemRequest itemRequest) {
    final Optional<PermissionItemResponse> permissionProj =
        permissionService.updatePermission(id, itemRequest);
    return permissionProj
        .map(proj -> ResponseEntity.status(HttpStatus.ACCEPTED).body(proj))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
