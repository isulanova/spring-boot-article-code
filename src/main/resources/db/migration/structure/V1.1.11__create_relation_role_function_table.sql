SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
BEGIN
            RAISE
NOTICE 'CREATE TABLE relation_role_function';

drop table if exists relation_role_function cascade;

create table relation_role_function
(
    id             uuid not null
        primary key,
    function_id    uuid
        constraint fk_relation_role_function_function
            references system_function,
    role_id        uuid
        constraint fk_relation_role_function_role
            references system_role,
    record_created timestamp,
    record_updated timestamp,
    record_status  varchar(255)
);

comment
on column relation_role_function.id is 'role-function relation id';
comment
on column relation_role_function.function_id is 'role function function id in system_function';
comment
on column relation_role_function.role_id is 'role function role id in system_role';
comment
on column relation_role_function.record_created
            is 'inserted record timestamp (current datetime)';
comment
on column relation_role_function.record_updated is 'record_updated record timestamp (default - current datetime)';
comment
on column relation_role_function.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
comment
on table relation_role_function is 'role function relation';

RAISE
NOTICE 'TABLE relation_role_function created success';
END
$$;