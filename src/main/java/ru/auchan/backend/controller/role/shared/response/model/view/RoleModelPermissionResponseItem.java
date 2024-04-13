package ru.auchan.backend.controller.role.shared.response.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(title = "[ROLE-MODEL] role model permission response item")
public class RoleModelPermissionResponseItem
    implements Serializable, Comparable<RoleModelPermissionResponseItem> {

  @JsonProperty("id")
  private UUID permissionId;

  @JsonProperty("name")
  private String permissionName;

  @JsonProperty("description")
  private String description;

  @JsonIgnore @Getter private String systemName;

  @Builder.Default
  @JsonProperty("roles")
  private Set<RoleModelRoleResponseItem> roles = new HashSet<>();

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RoleModelPermissionResponseItem that = (RoleModelPermissionResponseItem) o;
    return Objects.equals(permissionId, that.permissionId)
        && Objects.equals(permissionName, that.permissionName)
        && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(permissionId, permissionName, description);
  }

  @Override
  public int compareTo(final RoleModelPermissionResponseItem o) {
    return Comparator.comparing(RoleModelPermissionResponseItem::getDescription)
        .thenComparing(RoleModelPermissionResponseItem::getPermissionName)
        .compare(this, o);
  }
}
