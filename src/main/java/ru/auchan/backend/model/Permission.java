package ru.auchan.backend.model;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.auchan.backend.io.entity.base.DbRowStatus;

@Getter
@Setter
@NoArgsConstructor
public class Permission {

  private UUID id;

  private LocalDate created;

  private LocalDate updated;

  private DbRowStatus status;

  private String systemName;

  private String uiName;

  private String description;

  public Permission(String systemName, String uiName, String description) {
    this.systemName = systemName;
    this.uiName = uiName;
    this.description = description;
  }
}
