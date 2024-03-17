package ru.auchan.backend.io.entity.role;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.relations.RoleGroupRelationEntity;

@Getter
@Setter
@Table(name = "system_group")
public class GroupEntity extends BaseEntity {

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Builder.Default
  @OneToMany(mappedBy = "group")
  private Set<RoleGroupRelationEntity> groups = new HashSet<>();

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
    final GroupEntity group = (GroupEntity) o;
    return Objects.equals(name, group.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
