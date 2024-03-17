package ru.auchan.backend.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.auchan.backend.controller.metadata.RoleModuleControllerMetadata;
import ru.auchan.backend.controller.shared.response.RoleModuleItemResponse;
import ru.auchan.backend.service.role.IRoleModuleService;

@RestController
@RequiredArgsConstructor
public class RoleModuleController implements RoleModuleControllerMetadata {

  private final IRoleModuleService moduleService;
  private final ModelMapper mapper;

  public ResponseEntity<List<RoleModuleItemResponse>> getModuleList() {
    return ResponseEntity.ok(
        moduleService.list().stream()
            .map(item -> mapper.map(item, RoleModuleItemResponse.class))
            .toList());
  }
}
