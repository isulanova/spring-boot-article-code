package ru.auchan.backend.io.entity.relations;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.auchan.backend.io.entity.PermissionEntity;
import ru.auchan.backend.io.entity.PermissionGroupEntity;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "system_role_model")
public class RoleModelRelationEntity extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "role_system_name", referencedColumnName = "system_name")
  private RoleEntity role;

  @ManyToOne
  @JoinColumn(name = "permission_group_system_name", referencedColumnName = "system_name")
  private PermissionGroupEntity permissionGroup;

  @ManyToOne
  @JoinColumn(name = "permission_system_name", referencedColumnName = "system_name")
  private PermissionEntity permission;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final RoleModelRelationEntity roleModel = (RoleModelRelationEntity) o;
    return Objects.equals(role, roleModel.role)
        && Objects.equals(permissionGroup, roleModel.permissionGroup)
        && Objects.equals(permission, roleModel.permission);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), role, permissionGroup, permission);
  }
}
