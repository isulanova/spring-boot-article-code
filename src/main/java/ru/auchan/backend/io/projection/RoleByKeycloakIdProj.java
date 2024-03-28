package ru.auchan.backend.io.projection;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import ru.auchan.backend.controller.role.shared.response.RoleSimpleItemResponse;

public interface RoleByKeycloakIdProj {

  @Value("#{target.keycloak_id}")
  String getId();

  @Value("#{@roleItemBaseMapper.transform(target.roles)}")
  List<RoleSimpleItemResponse> getRoles();
}
