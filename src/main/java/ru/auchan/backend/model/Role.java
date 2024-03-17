package ru.auchan.backend.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {

  private UUID id;

  private String systemName;

  private String uiName;

  private String description;

  public Role(String systemName, String uiName, String description) {
    this.systemName = systemName;
    this.uiName = uiName;
    this.description = description;
  }
}
