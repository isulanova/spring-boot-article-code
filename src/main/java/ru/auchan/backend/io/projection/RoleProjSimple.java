package ru.auchan.backend.io.projection;

import org.springframework.beans.factory.annotation.Value;

public interface RoleProjSimple {

  @Value("#{target.id}")
  String getId();

  @Value("#{target.name}")
  String getName();
}
