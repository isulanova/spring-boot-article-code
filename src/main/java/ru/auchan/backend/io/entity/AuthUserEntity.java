package ru.auchan.backend.io.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.auchan.backend.io.entity.base.EntityChangeEventFields;
import ru.auchan.backend.io.entity.role.RoleEntity;

@Getter
@Setter
@Entity
@Table(name = "system_usr")
public class AuthUserEntity extends EntityChangeEventFields implements Serializable {

  @Id
  @Column(name = "KEYCLOAK_ID")
  @JdbcTypeCode(SqlTypes.UUID)
  private UUID keycloakId;

  @Column(name = "USER_NAME")
  private String userName;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "system_user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> userRoles = new HashSet<>();

  @Override
  public String toString() {
    return "{" + "keycloakId=" + keycloakId + ", userName='" + userName + '\'' + '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final AuthUserEntity authUser = (AuthUserEntity) o;
    return Objects.equals(keycloakId, authUser.keycloakId)
        && Objects.equals(userName, authUser.userName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keycloakId, userName);
  }
}
