package ru.auchan.backend.io.entity.role;

import jakarta.persistence.Column;
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
import ru.auchan.backend.model.RoleModule;

@Getter
@Setter
@Entity
@Table(name = "system_module")
public class ModuleEntity extends BaseEntity {
  @Column(name = "system_name", unique = true, nullable = false)
  private String systemName;

  @Column(name = "label")
  private String label;

  @Builder.Default
  @OneToMany(mappedBy = "module")
  private Set<RoleModule> modules = new HashSet<>();

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
    final ModuleEntity module = (ModuleEntity) o;
    return Objects.equals(systemName, module.systemName) && Objects.equals(label, module.label);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), systemName, label);
  }
}
