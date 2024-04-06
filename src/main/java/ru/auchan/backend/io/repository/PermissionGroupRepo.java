package ru.auchan.backend.io.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.auchan.backend.io.entity.PermissionGroupEntity;

@Repository
public interface PermissionGroupRepo extends JpaRepository<PermissionGroupEntity, UUID> {

  Optional<PermissionGroupEntity> findByNameOrAlias(String name, String alias);

  Optional<PermissionGroupEntity> findByName(String name);
}
