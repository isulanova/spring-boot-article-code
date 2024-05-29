package ru.auchan.backend.service.permission.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.permission.shared.request.PermissionGroupRequest;
import ru.auchan.backend.controller.permission.shared.response.PermissionGroupResponse;
import ru.auchan.backend.io.entity.PermissionGroupEntity;
import ru.auchan.backend.io.repository.PermissionGroupRepo;
import ru.auchan.backend.service.permission.IPermissionGroupService;
import ru.auchan.backend.service.permission.exception.PermissionGroupAlreadyExistException;
import ru.auchan.backend.service.permission.exception.PermissionGroupNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionGroupService implements IPermissionGroupService {

  private final PermissionGroupRepo groupRepo;
  private final ModelMapper mapper;

  @Override
  public List<PermissionGroupResponse> findAll() {
    return groupRepo.findAll().stream()
        .map(item -> mapper.map(item, PermissionGroupResponse.class))
        .toList();
  }

  @Override
  public PermissionGroupResponse findById(UUID id) {
    return groupRepo
        .findById(id)
        .map(item -> mapper.map(item, PermissionGroupResponse.class))
        .orElseThrow(
            () ->
                new PermissionGroupNotFoundException(
                    "Permission group %s not found".formatted(id)));
  }

  @Override
  public void delete(UUID id) {
    groupRepo.deleteById(id);
  }

  @Override
  public PermissionGroupResponse add(PermissionGroupRequest request) {
    final Optional<PermissionGroupEntity> groupByNameOrAlias =
        groupRepo.findByNameOrAlias(request.getName(), request.getAlias());

    if (groupByNameOrAlias.isPresent()) {
      throw new PermissionGroupAlreadyExistException(
          "Permission group %s already exists".formatted(request));
    }
    return mapper.map(
        groupRepo.save(mapper.map(request, PermissionGroupEntity.class)),
        PermissionGroupResponse.class);
  }

  @Override
  public Optional<PermissionGroupEntity> findBySystemNameDb(String systemName) {
    return groupRepo.findByName(systemName);
  }

  @Override
  public Optional<PermissionGroupEntity> findByIdDb(UUID id) {
    return groupRepo.findById(id);
  }
}
