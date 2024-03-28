package ru.auchan.backend.service.access.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAccessMap {

  private String userId;

  private List<String> permissions;

  private List<String> userRoles;
}
