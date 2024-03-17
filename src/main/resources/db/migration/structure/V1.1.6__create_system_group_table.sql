SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
BEGIN
            RAISE
NOTICE 'CREATE TABLE system_group';

drop table if exists system_group cascade;

create table system_group
(
    id             uuid         not null
        primary key,
    name           varchar(255) not null
        constraint uk_system_group_name
            unique,
    record_created timestamp,
    record_updated timestamp,
    record_status  varchar(255)
);

comment
on column system_group.id is 'role group id';
comment
on column system_group.name is 'role group name';
comment
on column system_group.record_created
            is 'inserted record timestamp (current datetime)';
comment
on column system_group.record_updated is 'record_updated record timestamp (default - current datetime)';
comment
on column system_group.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
comment
on table system_group is 'role group';

RAISE
NOTICE 'TABLE system_group created success';
END
$$;