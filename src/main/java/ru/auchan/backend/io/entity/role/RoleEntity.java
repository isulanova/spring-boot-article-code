package ru.auchan.backend.io.entity.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.AuthUserEntity;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.relations.RoleModelRelationEntity;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_role")
public class RoleEntity extends BaseEntity implements Serializable {

  @Column(name = "system_name", unique = true, nullable = false)
  private String systemName;

  @Column(name = "ui_name", unique = true, nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToMany(mappedBy = "userRoles")
  private Set<AuthUserEntity> user;

  @Builder.Default
  @OneToMany(mappedBy = "role")
  private Set<RoleModelRelationEntity> model = new HashSet<>();
}
