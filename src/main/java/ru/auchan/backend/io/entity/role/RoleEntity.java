package ru.auchan.backend.io.entity.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.auchan.backend.io.entity.AuthUserEntity;
import ru.auchan.backend.io.entity.base.BaseEntity;
import ru.auchan.backend.io.entity.relations.RoleFunctionRelationEntity;
import ru.auchan.backend.io.entity.relations.RoleGroupRelationEntity;
import ru.auchan.backend.io.entity.relations.RoleModelRelationEntity;
import ru.auchan.backend.io.entity.relations.RoleModuleRelationEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "system_role")
public class RoleEntity extends BaseEntity {

    @Column(name = "system_name", unique = true, nullable = false)
    private String systemName;

    @Column(name = "ui_name", unique = true, nullable = false)
    private String uiName;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "userRoles")
    private Set<AuthUserEntity> user;

    @Builder.Default
    @OneToMany(mappedBy = "role")
    private Set<RoleModelRelationEntity> model = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "role")
    private Set<RoleFunctionRelationEntity> functions = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "role")
    private Set<RoleGroupRelationEntity> groups = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "role")
    private Set<RoleModuleRelationEntity> modules = new HashSet<>();
}
