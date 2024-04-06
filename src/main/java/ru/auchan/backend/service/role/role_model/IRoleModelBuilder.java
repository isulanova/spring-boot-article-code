package ru.auchan.backend.service.role.role_model;

import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystem;
import ru.auchan.backend.controller.role.shared.response.model.view.RoleModelResponse;

public interface IRoleModelBuilder {

  RoleModelResponse getRoleModelUi();

  RoleModelSystem getRoleModelSystem();
}
