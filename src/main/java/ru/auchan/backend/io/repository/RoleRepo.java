package ru.auchan.backend.io.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.auchan.backend.io.entity.role.RoleEntity;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, UUID> {

  Optional<RoleEntity> findByName(String systemName);

  @Query(
      value =
          """
              SELECT
                users.keycloak_id\\:\\:text,
                json_agg(json_build_object('id' , sr.id, 'name', sr.system_name))\\:\\:text as roles
              FROM {h-schema}system_usr users
                       JOIN {h-schema}system_user_role sur on users.keycloak_id = sur.user_id
                       JOIN {h-schema}system_role sr on sur.role_id = sr.id where keycloak_id = :id
              group by users.keycloak_id""",
      nativeQuery = true)
  List<RoleEntity> findByKeycloakId(@Param("id") UUID id);

  Set<RoleEntity> findByNameIn(Set<String> roleNames);
}
