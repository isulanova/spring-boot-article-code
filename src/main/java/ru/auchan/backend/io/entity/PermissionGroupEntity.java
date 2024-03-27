package ru.auchan.backend.io.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.relations.RoleModelRelationEntity;

@Getter
@Setter
@Entity
@Table(name = "system_permission_group")
public class PermissionGroupEntity extends BaseEntity implements Serializable {

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "alias", nullable = false)
  private String alias;

  @Builder.Default
  @OneToMany(mappedBy = "permissionGroup")
  private Set<RoleModelRelationEntity> model = new HashSet<>();

  @Override
  public String toString() {
    return "{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
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
    final PermissionGroupEntity that = (PermissionGroupEntity) o;
    return Objects.equals(name, that.name) && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, description);
  }
}
