SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
BEGIN
            RAISE
NOTICE 'CREATE TABLE relation_role_group';

drop table if exists relation_role_group cascade;

create table relation_role_group
(
    id             uuid not null
        primary key,
    group_id       uuid
        constraint fk_relation_role_group_group
            references system_group,
    role_id        uuid
        constraint fk_relation_role_group_role
            references system_role,
    record_created timestamp,
    record_updated timestamp,
    record_status  varchar(255)
);

comment
on column relation_role_group.id is 'role-group relation id';
comment
on column relation_role_group.group_id is 'role group group id in system_group';
comment
on column relation_role_group.role_id is 'role group role id in system_role';
comment
on column relation_role_group.record_created
            is 'inserted record timestamp (current datetime)';
comment
on column relation_role_group.record_updated is 'record_updated record timestamp (default - current datetime)';
comment
on column relation_role_group.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
comment
on table relation_role_group is 'relation role group';

RAISE
NOTICE 'TABLE relation_role_group created success';
END
$$;