package ru.auchan.backend.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.auchan.backend.controller.shared.response.role.RoleGroupItemResponse;
import ru.auchan.backend.service.role.IRoleGroupService;

@Slf4j
@Service
public class RoleGroupService implements IRoleGroupService {
    @Override
    public List<RoleGroupItemResponse> list() {
    return null;
    }
}
