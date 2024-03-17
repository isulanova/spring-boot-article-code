package ru.auchan.backend.service.role;

import java.util.List;
import ru.auchan.backend.controller.shared.response.role.RoleGroupItemResponse;

public interface IRoleGroupService {

  List<RoleGroupItemResponse> list();
}
