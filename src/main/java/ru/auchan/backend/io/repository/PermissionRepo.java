package ru.auchan.backend.io.repository;

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
import ru.auchan.backend.io.entity.PermissionEntity;
import ru.auchan.backend.io.projection.PermissionProj;

@Repository
public interface PermissionRepo extends JpaRepository<PermissionEntity, UUID> {

  @Query(
      value =
          """
            SELECT
              id\\:\\:text,
              system_name,
              ui_name,
              description
            FROM {h-schema}system_permission
            ORDER BY system_name""",
      nativeQuery = true)
  Page<PermissionProj> getPermissionListProj(Pageable pageable);

  Optional<PermissionEntity> findBySystemName(String name);

  List<PermissionEntity> findBySystemNameIn(Set<String> systemNames);

  @Query(
      value =
          """
              SELECT sp.*
              FROM {h-schema}system_permission sp
                       LEFT JOIN {h-schema}system_permission_group spg on spg.alias
                  = substr(sp.system_name, position(spg.alias IN sp.system_name),
                           length(spg.alias))
              WHERE spg.alias || '_' in (select str || '_' from unnest(string_to_array(:groupList, ',')) as str)
                AND position(spg.alias || '_' IN sp.system_name) = 1""",
      nativeQuery = true)
  List<PermissionEntity> getPermissionListByPermissionGroupList(
      @Param("groupList") List<String> groupList);

  @Query(
      value =
          """
            SELECT
              id\\:\\:text,
              system_name,
              ui_name,
              description
            FROM {h-schema}system_permission
            WHERE id=:id""",
      nativeQuery = true)
  Optional<PermissionProj> findByIdentifier(@Param("id") UUID id);

  @Query(
      value =
          """
              SELECT sp.system_name
              FROM {h-schema}admin_view_permission aw
              JOIN {h-schema}system_permission sp on aw.permission_id = sp.id AND is_checked = true
              WHERE role_id IN (:roleIds) group by sp.system_name ORDER BY sp.system_name;""",
      nativeQuery = true)
  Set<String> findByRoleIds(@Param("roleIds") List<UUID> roleIds);
}
