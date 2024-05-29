package ru.auchan.backend.controller.role.shared.response.model.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "[ROLE-MODEL] role model group response item")
public class RoleModelGroupResponseItem
    implements Serializable, Comparable<RoleModelGroupResponseItem> {

  @JsonProperty("id")
  private UUID groupId;

  @JsonProperty("name")
  private String groupName;

  @Builder.Default
  @JsonProperty("permissions")
  private Set<RoleModelPermissionResponseItem> permissions = new TreeSet<>();

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RoleModelGroupResponseItem that = (RoleModelGroupResponseItem) o;
    return Objects.equals(groupId, that.groupId) && Objects.equals(groupName, that.groupName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, groupName);
  }

  @Override
  public int compareTo(final RoleModelGroupResponseItem o) {
    return this.groupName.compareTo(o.groupName);
  }
}
