package ru.auchan.backend.io.entity.relations;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.ModuleEntity;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;


@Getter
@Setter
@Entity
@Table(name = "relation_role_module")
public class RoleModuleRelationEntity extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "module_id")
  private ModuleEntity module;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private RoleEntity role;

  public void setGroup(final ModuleEntity module) {
    this.module = module;
    this.module.getModules().add(this);
  }

  public void setRole(final RoleEntity role) {
    this.role = role;
    this.role.getModules().add(this);
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
    final RoleModuleRelationEntity that = (RoleModuleRelationEntity) o;
    return Objects.equals(module, that.module) && Objects.equals(role, that.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), module, role);
  }
}
