package ru.auchan.backend.io.entity.role;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.relations.RoleFunctionRelationEntity;

@Getter
@Setter
@Entity
@Table(name = "system_function")
public class FunctionEntity extends BaseEntity {

  private String name;

  @Builder.Default
  @OneToMany(mappedBy = "function")
  private Set<RoleFunctionRelationEntity> functions = new HashSet<>();

  @Override
  public String toString() {
    return "{" + "name='" + name + '\'' + '}';
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
    final FunctionEntity function = (FunctionEntity) o;
    return Objects.equals(name, function.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
