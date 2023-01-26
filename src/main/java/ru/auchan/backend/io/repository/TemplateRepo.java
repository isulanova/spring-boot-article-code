package ru.auchan.backend.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.auchan.backend.io.entity.TemplateEntity;

import java.util.UUID;

@Repository
public interface TemplateRepo
        extends JpaRepository<TemplateEntity, UUID>, JpaSpecificationExecutor<TemplateEntity> {
}
