package ru.auchan.backend.service;

import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.shared.request.view.RoleModelUpdatePermissionRequest;
import ru.auchan.backend.controller.shared.response.role.model.system.RoleModelSystem;
import ru.auchan.backend.controller.shared.response.role.model.view.RoleModelResponse;

@Slf4j
@Service
public class RoleModelService implements IRoleModelService {
    @Override
    public Set<UUID> updatePermission(RoleModelUpdatePermissionRequest roleModelRequest) {
    return null;
    }

    @Override
    public RoleModelResponse getRoleModel() {
        return null;
    }

    @Override
    public RoleModelSystem getRoleModelSystem() {
        return null;
    }

    @Override
    public void applyRoleModel(RoleModelSystem roleModelSystem) {

    }
}
