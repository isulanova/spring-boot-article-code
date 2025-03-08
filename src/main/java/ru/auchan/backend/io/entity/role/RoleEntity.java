package ru.auchan.backend.io.entity.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.AuthUserEntity;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.relations.RoleModelRelationEntity;

@Getter
@Setter
@Entity
@Table(name = "system_role")
public class RoleEntity extends BaseEntity implements Serializable {

  @Column(name = "system_name", unique = true, nullable = false)
  private String name;

  @Column(name = "ui_name", unique = true, nullable = false)
  private String label;

  @Column(name = "description")
  private String description;

  @ManyToMany(mappedBy = "userRoles")
  private Set<AuthUserEntity> user;

  @OneToMany(mappedBy = "role")
  private Set<RoleModelRelationEntity> model = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RoleEntity that)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    return Objects.equals(name, that.name)
        && Objects.equals(label, that.label)
        && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, label, description);
  }
}
