SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
BEGIN
            RAISE
NOTICE 'CREATE TABLE system_role_model';

drop table if exists system_role_model cascade;

create table if not exists system_role_model
(
    id                  uuid not null
    primary key,
    role_system_name             varchar(255)
    constraint fk_system_role_model_role
    references system_role (system_name),
    permission_group_name varchar(255)
    constraint fk_system_role_model_permission_group
    references system_permission_group (name),
    permission_system_name       varchar(255)
    constraint fk_system_role_model_permission
    references system_permission (system_name),
    record_created      timestamp,
    record_updated      timestamp,
    record_status       varchar(255)
    );

comment
on column system_role_model.id is 'role-model relation id';
comment
on column system_role_model.role_system_name is 'role-model role id in system_role';
comment
on column system_role_model.permission_group_name is 'role-model permission group id in system_permission_group';
comment
on column system_role_model.permission_system_name is 'role-model permission id in system_permission';
comment
on column system_role_model.record_created
            is 'inserted record timestamp (current datetime)';
comment
on column system_role_model.record_updated is 'record_updated record timestamp (default - current datetime)';
comment
on column system_role_model.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
comment
on table system_role_model is 'role-model relation';

RAISE
NOTICE 'TABLE system_role_model created success';
END
$$;

