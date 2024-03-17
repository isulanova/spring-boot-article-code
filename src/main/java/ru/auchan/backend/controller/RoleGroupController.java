package ru.auchan.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.metadata.RoleGroupControllerMetadata;
import ru.auchan.backend.controller.shared.response.role.RoleGroupItemResponse;
import ru.auchan.backend.service.role.IRoleGroupService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RoleGroupController implements RoleGroupControllerMetadata {

  private final IRoleGroupService groupService;

  public ResponseEntity<List<RoleGroupItemResponse>> getRoleGroupList() {
    return ResponseEntity.ok(groupService.list());
  }
}
