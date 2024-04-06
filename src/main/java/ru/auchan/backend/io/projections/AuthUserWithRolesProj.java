package ru.auchan.backend.io.projections;

import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import ru.auchan.backend.controller.role.shared.response.RoleItemResponse;

public interface AuthUserWithRolesProj extends AuthUserProj {

  @Value("#{@authUserRoleMapper.transform(target.roles)}")
  Set<RoleItemResponse> getRoles();
}
