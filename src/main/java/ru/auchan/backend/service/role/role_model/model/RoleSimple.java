package ru.auchan.backend.service.role.role_model.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleSimple {

  private UUID id;

  private String name;
}
