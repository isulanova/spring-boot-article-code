SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
BEGIN
            RAISE
NOTICE 'CREATE TABLE relation_role_module';

drop table if exists relation_role_module cascade;

create table relation_role_module
(
    id             uuid not null
        primary key,
    module_id      uuid
        constraint fk_relation_role_module_module
            references system_module,
    role_id        uuid
        constraint fk_relation_role_module_role
            references system_role,
    record_created timestamp,
    record_updated timestamp,
    record_status  varchar(255)
);

comment
on column relation_role_module.id is 'role-module relation id';
comment
on column relation_role_module.module_id is 'role module module id in system_module';
comment
on column relation_role_module.role_id is 'role module role id in system_role';
comment
on column relation_role_module.record_created
            is 'inserted record timestamp (current datetime)';
comment
on column relation_role_module.record_updated is 'record_updated record timestamp (default - current datetime)';
comment
on column relation_role_module.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
comment
on table relation_role_module is 'relation role module';

RAISE
NOTICE 'TABLE relation_role_module created success';
END
$$;