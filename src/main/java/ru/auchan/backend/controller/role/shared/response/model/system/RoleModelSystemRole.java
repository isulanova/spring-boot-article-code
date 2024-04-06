package ru.auchan.backend.controller.role.shared.response.model.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.service.role.model.Role;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleModelSystemRole implements Serializable, Comparable<RoleModelSystemRole> {

  @JsonProperty("systemName")
  private String systemName;

  public static RoleModelSystemRole fromModel(final Role role) {
    return RoleModelSystemRole.builder().systemName(role.getSystemName()).build();
  }

  @Override
  public int compareTo(final RoleModelSystemRole o) {
    return this.systemName.compareTo(o.getSystemName());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RoleModelSystemRole that = (RoleModelSystemRole) o;
    return Objects.equals(systemName, that.systemName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(systemName);
  }
}
