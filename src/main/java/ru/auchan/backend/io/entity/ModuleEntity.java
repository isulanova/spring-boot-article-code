package ru.auchan.backend.io.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.relations.RoleModuleRelationEntity;
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
  private Set<RoleModuleRelationEntity> modules = new HashSet<>();
}
