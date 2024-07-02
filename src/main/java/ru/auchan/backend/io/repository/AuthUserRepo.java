package ru.auchan.backend.io.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.auchan.backend.io.entity.AuthUserEntity;
import ru.auchan.backend.io.projections.AuthUserWithRolesProj;

@Repository
public interface AuthUserRepo extends JpaRepository<AuthUserEntity, UUID> {

  @Query(
      value =
          """
                  SELECT
                      su.keycloak_id\\:\\:text,
                      su.user_name,
                      json_agg(roles_ext.roles)\\:\\:text as roles
                  FROM
                      {h-schema}system_usr su
                  left JOIN
                      {h-schema}system_user_role sur
                      ON su.keycloak_id = sur.user_id
                  left JOIN (SELECT
                      users.id,
                      row_to_json(users) as roles
                        FROM (SELECT
                                sr.id,
                                sr.system_name as name,
                                sr.ui_name as label,
                                sr.description from {h-schema}system_role sr
                              ) users
                      ) AS roles_ext
                  ON roles_ext.id = sur.role_id
                  WHERE su.keycloak_id = :keycloakId
                  GROUP BY su.keycloak_id, su.user_name""",
      nativeQuery = true)
  Optional<AuthUserWithRolesProj> findByKeycloakId(@Param("keycloakId") UUID keycloakId);

  @Query(
      value =
          """
                  SELECT
                      su.keycloak_id\\:\\:text,
                      su.user_name,
                      json_agg(roles_ext.roles)\\:\\:text as roles
                  FROM
                      {h-schema}system_usr su
                  left JOIN
                      {h-schema}system_user_role sur
                      ON su.keycloak_id = sur.user_id
                  left JOIN (SELECT
                      users.id,
                      row_to_json(users) as roles
                        FROM (SELECT
                                sr.id,
                                sr.system_name as name,
                                sr.ui_name as label,
                                sr.description from {h-schema}system_role sr
                              ) users
                      ) AS roles_ext
                  ON roles_ext.id = sur.role_id
                  WHERE su.keycloak_id IN (:keycloakIds)
                  GROUP BY su.keycloak_id, su.user_name""",
      nativeQuery = true)
  List<AuthUserWithRolesProj> findByKeycloakIdList(@Param("keycloakIds") List<UUID> keycloakIds);

  @Query(
      value =
          """
                  SELECT
                      su.keycloak_id\\:\\:text,
                      su.user_name,
                      json_agg(roles_ext.roles)\\:\\:text as roles
                  FROM
                      {h-schema}system_usr su
                  LEFT JOIN
                      {h-schema}system_user_role sur
                      ON su.keycloak_id = sur.user_id
                  LEFT JOIN (SELECT
                      users.id,
                      row_to_json(users) as roles
                        FROM (SELECT
                                sr.id,
                                sr.system_name as name,
                                sr.ui_name as label,
                                sr.description from {h-schema}system_role sr
                              ) users
                      ) AS roles_ext
                  ON roles_ext.id = sur.role_id
                  WHERE su.user_name like concat('%', :userName , '%')
                  GROUP BY su.keycloak_id, su.user_name""",
      nativeQuery = true)
  Page<AuthUserWithRolesProj> findByUserName(@Param("userName") String userName, Pageable pageable);

  @Query(
      value =
          """
                        SELECT
                          q.keycloak_id\\:\\:text,
                          q.user_name,
                          q.roles\\:\\:text
                        FROM
                              (SELECT
                                   su.keycloak_id,
                                   su.user_name,
                                   json_agg(roles_ext.roles) as roles
                               FROM
                                  {h-schema}system_usr su
                                LEFT JOIN
                                    {h-schema}system_user_role sur
                                    ON su.keycloak_id = sur.user_id
                                LEFT JOIN (SELECT
                                    users.id,
                                    row_to_json(users) as roles
                                      FROM (SELECT
                                              sr.id,
                                              sr.system_name as name,
                                              sr.ui_name as label,
                                              sr.description from {h-schema}system_role sr
                                            ) users
                                    ) AS roles_ext
                                ON roles_ext.id = sur.role_id
                                GROUP BY su.keycloak_id, su.user_name) q,
                                  json_array_elements(q.roles) as r
                          WHERE (r ->>'id')\\:\\:uuid IN (:roleId)""",
      nativeQuery = true)
  Page<AuthUserWithRolesProj> findByUserRole(@Param("roleId") UUID roleId, Pageable pageable);

  @Query(
      value =
          """
            select
              distinct user_id\\:\\:text
            from {h-schema}system_user_role
            where role_id in (:roleIds)""",
      nativeQuery = true)
  Set<UUID> findUserIdListByRoleId(@Param("roleIds") List<UUID> roleIds);

  @Query(
      value =
          """
              SELECT
                su.keycloak_id\\:\\:text
              FROM {h-schema}system_usr su
                       join {h-schema}system_user_role sur on su.keycloak_id = sur.user_id
                       join {h-schema}system_role sr on sur.role_id = sr.id
              WHERE sr.system_name in (:roleNames)""",
      nativeQuery = true)
  Set<UUID> findUserIdListByRoleNames(@Param("roleNames") List<String> roleNames);

  @Query(
      value =
          """
                  SELECT
                      su.keycloak_id\\:\\:text,
                      su.user_name,
                      json_agg(roles_ext.roles)\\:\\:text as roles
                  FROM
                      {h-schema}system_usr su
                  LEFT JOIN
                      {h-schema}system_user_role sur
                      ON su.keycloak_id = sur.user_id
                 LEFT JOIN (SELECT
                      users.id,
                      row_to_json(users) as roles
                        FROM (SELECT
                                sr.id,
                                sr.system_name as name,
                                sr.ui_name as label,
                                sr.description from {h-schema}system_role sr
                              ) users
                      ) AS roles_ext
                  ON roles_ext.id = sur.role_id
                  GROUP BY su.keycloak_id, su.user_name""",
      nativeQuery = true)
  Page<AuthUserWithRolesProj> list(Pageable pageable);

  @Query(
      value =
          """
          SELECT DISTINCT u.keycloak_id
          FROM system_usr u
                   LEFT JOIN system_user_role sur ON sur.user_id = u.keycloak_id
                   LEFT JOIN system_role sr ON sr.id = sur.role_id
                   LEFT JOIN system_role_model srm ON srm.role_system_name = sr.system_name
          WHERE u.keycloak_id IN (:keycloakUserIds) AND srm.permission_group_name IN (:groupNames)
      """,
      nativeQuery = true)
  Set<UUID> filteringByGroupName(
      @Param("keycloakUserIds") Collection<UUID> keycloakUserIds,
      @Param("groupNames") Collection<String> groupNames);
}
