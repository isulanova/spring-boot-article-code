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
import ru.auchan.backend.io.projection.RoleByKeycloakIdProj;
import ru.auchan.backend.io.projection.RoleProj;
import ru.auchan.backend.io.projection.RoleProjSimple;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, UUID> {

  @Query(
      value =
          """
              SELECT
                     sr.id\\:\\:text,
                     string_agg(sf.name, ',') as functions,
                     sr.system_name as name,
                     sr.ui_name as label,
                     sr.description as description,
                     sg.name as role_group,
                     sm.label as module
              FROM {h-schema}system_role sr
                       LEFT JOIN {h-schema}relation_role_group rrg on sr.id = rrg.role_id
                       LEFT JOIN {h-schema}system_group sg on rrg.group_id = sg.id
                       LEFT JOIN {h-schema}relation_role_module rrm on sr.id = rrm.role_id
                       LEFT JOIN {h-schema}system_module sm on rrm.module_id = sm.id
                       LEFT JOIN {h-schema}relation_role_function rrf on sr.id = rrf.role_id
                       LEFT JOIN {h-schema}system_function sf on rrf.function_id = sf.id
              GROUP BY sr.id, sr.system_name, sg.name, sm.label""",
      nativeQuery = true)
  List<RoleProj> getRoleListProj();

  @Query(
      value =
          """
              SELECT
                     sr.id\\:\\:text    as id,
                     sr.system_name as name,
                     sr.ui_name     as label,
                     sr.description as description,
                     srg.name       as role_group,
                     srm.label      as module
              FROM {h-schema}system_role sr
                       LEFT JOIN {h-schema}system_role_system_role_group sr_srg
                                 ON sr.id = sr_srg.role_id
                       LEFT JOIN {h-schema}system_role_group srg ON sr_srg.system_role_group_id = srg.id
                       LEFT JOIN {h-schema}system_role_system_role_module srsrm on sr.id = srsrm.role_id
                       LEFT JOIN {h-schema}system_role_module srm ON srsrm.system_role_module_id = srm.id
               WHERE srm.system_name = upper(:module) AND srg.name = upper(:group) """,
      nativeQuery = true)
  List<RoleProj> getRoleListProjByModuleAndGroup(
      @Param("module") String module, @Param("group") String group);

  Optional<RoleEntity> findBySystemName(String systemName);

  @Query(
      value =
          """
            SELECT
              id\\:\\:text as id,
              system_name as name,
              ui_name as label,
              description as description
            FROM {h-schema}system_role WHERE id=:id""",
      nativeQuery = true)
  Optional<RoleProj> findByIdentifier(@Param("id") UUID id);

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
  Optional<RoleByKeycloakIdProj> findByKeycloakId(@Param("id") UUID id);

  @Query(
      value =
          """
              SELECT
                role.id\\:\\:text as id,
                role.system_name as name
              FROM {h-schema}system_usr usr
                  JOIN {h-schema}system_user_role user_role
                      ON usr.keycloak_id = user_role.user_id
                  JOIN {h-schema}system_role role ON user_role.role_id = role.id
              WHERE usr.keycloak_id = :id""",
      nativeQuery = true)
  List<RoleProjSimple> getRolesByUser(@Param("id") UUID id);

  Set<RoleEntity> findBySystemNameIn(Set<String> roleNames);
}
