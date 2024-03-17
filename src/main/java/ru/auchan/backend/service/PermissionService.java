package ru.auchan.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.shared.request.permission.PermissionItemRequest;
import ru.auchan.backend.controller.shared.response.permission.PermissionItemResponse;
import ru.auchan.backend.io.entity.PermissionEntity;
import ru.auchan.backend.io.projection.PermissionProj;
import ru.auchan.backend.io.repository.PermissionRepo;
import ru.auchan.backend.model.Permission;
import ru.auchan.backend.service.exception.PermissionAlreadyExistsException;
import ru.auchan.backend.service.exception.PermissionNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {
  private final PermissionRepo permissionRepo;
  private final ModelMapper mapper;

  @Override
  public Page<PermissionItemResponse> list(final Pageable pageable) {
    return permissionRepo
        .getPermissionListProj(pageable)
        .map(PermissionItemResponse::fromProjection);
  }

  @Override
  public List<Permission> list() {
    return permissionRepo.findAll().stream()
        .map(item -> mapper.map(item, Permission.class))
        .toList();
  }

  @Override
  public List<Permission> getPermissionListByPermissionGroupList(final List<String> groupList) {
    return permissionRepo.getPermissionListByPermissionGroupList(groupList).stream()
        .map(item -> mapper.map(item, Permission.class))
        .toList();
  }

  @Override
  public Optional<Permission> findByIdDb(final UUID id) {
    return permissionRepo.findById(id).map(item -> mapper.map(mapper, Permission.class));
  }

  @Override
  public Optional<Permission> findBySystemName(final String permissionName) {
    return permissionRepo.findBySystemName(permissionName).map(item -> mapper.map(mapper, Permission.class));
  }

  @Override
  public Set<String> findByRoleIds(final List<UUID> roleIds) {
    return permissionRepo.findByRoleIds(roleIds);
  }

  @Override
  public Optional<PermissionItemResponse> addPermission(final PermissionItemRequest itemRequest) {
    log.info("Trying to create permission with params: {}", itemRequest);
    final Optional<Permission> permissionFromDb =
        permissionRepo.findBySystemName(itemRequest.getSystemName()).map(item -> mapper.map(mapper, Permission.class));;
    if (permissionFromDb.isPresent()) {
      log.error("Permission: {} already exists", itemRequest.getSystemName());
      throw new PermissionAlreadyExistsException(itemRequest.getSystemName());
    }
    final PermissionEntity persistentEntity =
        permissionRepo.save(mapper.map(
            new Permission(
                itemRequest.getSystemName(),
                itemRequest.getUiName(),
                itemRequest.getDescription()), PermissionEntity.class));
    return findById(persistentEntity.getId());
  }

  @Override
  public boolean removePermission(final UUID id) {
    final Optional<PermissionProj> permissionFromDb = permissionRepo.findByIdentifier(id);
    if (permissionFromDb.isEmpty()) {
      log.error("Permission with id: {} not found", id);
      throw new PermissionNotFoundException(id);
    }
    permissionRepo.deleteById(id);
    return !permissionRepo.existsById(id);
  }

  @Override
  public Optional<PermissionItemResponse> findById(final UUID uuid) {
    final Optional<PermissionProj> projOptional = permissionRepo.findByIdentifier(uuid);
    if (projOptional.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(PermissionItemResponse.fromProjection(projOptional.get()));
  }

  @Override
  public Optional<PermissionItemResponse> updatePermission(
      final UUID id, final PermissionItemRequest itemRequest) {
    final Optional<PermissionEntity> permissionFromDbOptional = permissionRepo.findById(id);
    if (permissionFromDbOptional.isEmpty()) {
      log.error("Permission: {} not found", id);
      throw new PermissionNotFoundException(id);
    } else {
      final PermissionEntity permissionFromDb = permissionFromDbOptional.get();
      permissionFromDb.setDescription(itemRequest.getDescription());
      permissionFromDb.setSystemName(itemRequest.getSystemName());
      permissionFromDb.setUiName(itemRequest.getUiName());
      permissionRepo.save(permissionFromDb);
      return findById(permissionFromDb.getId());
    }
  }
}
