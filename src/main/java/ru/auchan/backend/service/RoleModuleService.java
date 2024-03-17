package ru.auchan.backend.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.auchan.backend.io.repository.RoleModuleRepo;
import ru.auchan.backend.model.RoleModule;
import ru.auchan.backend.service.role.IRoleModuleService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleModuleService implements IRoleModuleService {

  private final RoleModuleRepo roleModuleRepo;
  private final ModelMapper mapper;

  @Override
  public List<RoleModule> list() {
    return roleModuleRepo.findAll().stream()
        .map(item -> mapper.map(item, RoleModule.class))
        .toList();
  }
}
