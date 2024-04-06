package ru.auchan.backend.service.role.role_model;


import ru.auchan.backend.controller.role.shared.response.model.system.RoleModelSystem;

public interface IRoleModelApplier {

  void applyRoleModel(RoleModelSystem roleModelSystem);
}
