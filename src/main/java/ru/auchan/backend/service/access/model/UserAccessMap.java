package ru.auchan.backend.service.access.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccessMap {

  private String userId;

  private List<String> permissions;

  private List<String> userRoles;
}
