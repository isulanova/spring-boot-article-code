package ru.auchan.backend.controller.role.shared.response.model.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleModelSystemRole {

  @JsonProperty("systemName")
  private String systemName;
}
