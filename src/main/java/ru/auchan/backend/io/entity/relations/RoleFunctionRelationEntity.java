package ru.auchan.backend.io.entity.relations;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.role.FunctionEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;

@Getter
@Setter
@Entity
@Table(name = "relation_role_function")
public class RoleFunctionRelationEntity extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "function_id")
  private FunctionEntity function;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private RoleEntity role;

  public void setFunction(final FunctionEntity function) {
    this.function = function;
    this.function.getFunctions().add(this);
  }

  public void setRole(final RoleEntity role) {
    this.role = role;
    this.role.getFunctions().add(this);
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
    final RoleFunctionRelationEntity that = (RoleFunctionRelationEntity) o;
    return Objects.equals(function, that.function) && Objects.equals(role, that.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), function, role);
  }
}
