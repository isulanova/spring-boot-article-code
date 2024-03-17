package ru.auchan.backend.io.projection;

import org.springframework.beans.factory.annotation.Value;

public interface PermissionProj {

  @Value("#{target.id}")
  String getId();

  @Value("#{target.system_name}")
  String getSystemName();

  @Value("#{target.ui_name}")
  String getUiName();

  @Value("#{target.description}")
  String getDescription();
}
