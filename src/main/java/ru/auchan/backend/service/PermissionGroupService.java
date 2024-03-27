package ru.auchan.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.shared.request.PermissionGroupRequest;
import ru.auchan.backend.controller.shared.response.PermissionGroupResponse;
import ru.auchan.backend.model.PermissionGroup;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class PermissionGroupService implements IPermissionGroupService {
    @Override
    public List<PermissionGroupResponse> findAllResponse() {
        return null;
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
