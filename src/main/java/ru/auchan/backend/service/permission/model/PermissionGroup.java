package ru.auchan.backend.service.permission.model;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.base.DbRowStatus;

@Getter
@Setter
public class PermissionGroup {

  private UUID id;

  private LocalDate created;

  private LocalDate updated;

  private DbRowStatus status;

  private String name;

  private String description;
  private String alias;
}
