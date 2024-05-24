package ru.auchan.backend.controller.role.shared.response.model.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleModelSystemGroup implements Serializable, Comparable<RoleModelSystemGroup> {

  @JsonProperty("systemName")
  private String systemName;

  @Builder.Default
  @JsonProperty("permissions")
  private Set<RoleModelSystemPermission> permissions = new TreeSet<>();

  @Override
  public int compareTo(final RoleModelSystemGroup o) {
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
    final RoleModelSystemGroup that = (RoleModelSystemGroup) o;
    return Objects.equals(systemName, that.systemName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(systemName);
  }
}
