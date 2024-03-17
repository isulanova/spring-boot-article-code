package ru.auchan.backend.io.entity.relations;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.role.GroupEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;


@Getter
@Setter
@Entity
@Table(name = "relation_role_group")
public class RoleGroupRelationEntity extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "group_id")
  private GroupEntity group;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private RoleEntity role;

  public void setGroup(final GroupEntity group) {
    this.group = group;
    this.group.getGroups().add(this);
  }

  public void setRole(final RoleEntity role) {
    this.role = role;
    this.role.getGroups().add(this);
  }

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
    final RoleGroupRelationEntity roleGroup = (RoleGroupRelationEntity) o;
    return Objects.equals(group, roleGroup.group) && Objects.equals(role, roleGroup.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), group, role);
  }
}
