package ru.auchan.backend.controller.shared.response.role.model.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleModelSystem implements Serializable {

  @JsonProperty("permissionGroup")
  private Set<RoleModelSystemGroup> group = new HashSet<>();
}
