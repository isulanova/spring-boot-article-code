package ru.auchan.backend.io.projection;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;

public interface RoleProj extends RoleProjSimple {

  @Value("#{@roleFunctionMapper.transform(target.functions)}")
  List<String> getFunctions();

  @Value("#{target.name}")
  String getName();

  @Value("#{target.label}")
  String getLabel();

  @Value("#{target.description}")
  String getDescription();

  @Value("#{target.role_group}")
  String getGroup();

  @Value("#{target.module}")
  String getModule();
}
