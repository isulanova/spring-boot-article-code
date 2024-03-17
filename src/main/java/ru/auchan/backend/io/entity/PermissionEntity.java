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
@Table(name = "system_permission")
public class PermissionEntity extends BaseEntity implements Serializable {

  @Column(name = "system_name", unique = true, nullable = false)
  private String systemName;

  @Column(name = "ui_name", unique = true, nullable = false)
  private String uiName;

  @Column(name = "description")
  private String description;

  @Builder.Default
  @OneToMany(mappedBy = "permission")
  private Set<RoleModelRelationEntity> model = new HashSet<>();

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
    final PermissionEntity that = (PermissionEntity) o;
    return Objects.equals(systemName, that.systemName)
        && Objects.equals(uiName, that.uiName)
        && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), systemName, uiName, description);
  }
}
