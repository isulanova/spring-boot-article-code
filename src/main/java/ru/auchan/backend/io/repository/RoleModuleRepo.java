package ru.auchan.backend.io.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.auchan.backend.io.entity.ModuleEntity;

@Repository
public interface RoleModuleRepo extends JpaRepository<ModuleEntity, UUID> {

}
