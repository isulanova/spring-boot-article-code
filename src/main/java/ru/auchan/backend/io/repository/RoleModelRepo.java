package ru.auchan.backend.io.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.auchan.backend.io.entity.PermissionEntity;
import ru.auchan.backend.io.entity.PermissionGroupEntity;
import ru.auchan.backend.io.entity.relations.RoleModelRelationEntity;
import ru.auchan.backend.io.entity.role.RoleEntity;

@Repository
public interface RoleModelRepo extends JpaRepository<RoleModelRelationEntity, UUID> {

  List<RoleModelRelationEntity> findByPermissionAndPermissionGroup(
      PermissionEntity permission, PermissionGroupEntity permissionGroup);

  List<RoleModelRelationEntity> findByRoleIn(List<RoleEntity> roles);

  List<RoleModelRelationEntity> findByPermissionGroup(PermissionGroupEntity permissionGroup);

  Optional<RoleModelRelationEntity> findByPermissionAndPermissionGroupAndRole(
      PermissionEntity permission, PermissionGroupEntity permissionGroup, RoleEntity role);
}
