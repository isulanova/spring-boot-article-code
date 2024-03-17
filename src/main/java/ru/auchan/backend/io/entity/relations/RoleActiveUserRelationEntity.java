package ru.auchan.backend.io.entity.relations;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.AuthUserEntity;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;


@Getter
@Setter
@Entity
@Table(name = "system_active_user_per_role")
public class RoleActiveUserRelationEntity extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "role_id")
  private RoleEntity role;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private AuthUserEntity user;
}
