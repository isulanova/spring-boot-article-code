package ru.auchan.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.shared.request.PermissionGroupRequest;
import ru.auchan.backend.controller.shared.response.PermissionGroupResponse;
import ru.auchan.backend.io.repository.PermissionGroupRepo;
import ru.auchan.backend.model.PermissionGroup;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionGroupService implements IPermissionGroupService {

  private final PermissionGroupRepo groupRepo;
  private final ModelMapper mapper;
  @Override
  public List<PermissionGroupResponse> findAllResponse() {
    return groupRepo.findAll().stream()
        .map(item -> mapper.map(item, PermissionGroupResponse.class))
        .toList();
  }

  @Override
  public List<PermissionGroup> findAllData() {
    return null;
  }

  @Override
  public Optional<PermissionGroupResponse> findByIdProj(UUID id) {
    return Optional.empty();
  }

  @Override
  public Optional<PermissionGroup> findByIdCache(UUID id) {
    return Optional.empty();
  }

  @Override
  public Optional<PermissionGroup> findByIdDb(UUID id) {
    return Optional.empty();
  }

  @Override
  public Optional<PermissionGroup> findBySystemNameDb(String systemName) {
    return Optional.empty();
  }

  @Override
  public boolean delete(UUID id) {
    return false;
  }

  @Override
  public Optional<PermissionGroupResponse> add(PermissionGroupRequest request) {
    return Optional.empty();
  }

  @Override
  public Set<PermissionGroup> getPermissionsGroupByPermissionId(UUID permissionId) {
    return null;
  }
}
