package ru.auchan.backend.controller.shared.response.role.model.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.TreeSet;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleModelSystemPermission {

  @JsonProperty("systemName")
  private String systemName;

  @JsonProperty("roles")
  private Set<RoleModelSystemRole> roles = new TreeSet<>();
}
