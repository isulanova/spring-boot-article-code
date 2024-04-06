package ru.auchan.backend.io.projections;

import org.springframework.beans.factory.annotation.Value;

public interface AuthUserProj {

  @Value("#{target.keycloak_id}")
  String getKeycloakId();

  @Value("#{target.user_name}")
  String getUserName();
}
