SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
BEGIN
            RAISE
NOTICE 'CREATE TABLE system_permission';

drop table if exists system_permission cascade;

create table system_permission
(
    id uuid not null
        constraint system_permission_pkey
            primary key,
    system_name varchar(255) not null
        constraint uk_permission_system_name
            unique,
    ui_name varchar(255) not null,
    description varchar(255),
    record_created timestamp,
    record_updated timestamp,
    record_status varchar(255)
);

comment
on column system_permission.id is 'permission id';
        comment
on column system_permission.system_name is 'permission system name';
        comment
on column system_permission.ui_name is 'permission name in ui';
        comment
on column system_permission.description is 'permission description';
        comment
on column system_permission.record_created
            is 'inserted record timestamp (current datetime)';
        comment
on column system_permission.record_updated is 'record_updated record timestamp (default - current datetime)';
        comment
on column system_permission.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
        comment
on table system_permission is 'user permission table';

        RAISE
NOTICE 'TABLE system_permission created success';
END
$$;
