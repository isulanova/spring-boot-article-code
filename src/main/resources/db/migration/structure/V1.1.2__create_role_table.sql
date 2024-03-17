SET
    search_path TO
        ${schema};

----------------------------------------------------------------------------
DO
$$
    BEGIN
        RAISE
            NOTICE 'CREATE TABLE system_role';

        drop table if exists system_role cascade;

        create table system_role
        (
            id uuid not null
                constraint system_role_pkey
                    primary key,
            system_name varchar(255) not null
                constraint uk_system_name
                    unique,
            ui_name varchar(255) not null
                constraint uk_ui_name
                    unique,
            description varchar(255),
            record_created timestamp,
            record_updated timestamp,
            record_status varchar(255)
        );

        comment
            on column system_role.id is 'role id';
        comment
            on column system_role.system_name is 'role system name';
        comment
            on column system_role.ui_name is 'role name in ui';
        comment
            on column system_role.description is 'role description';
        comment
            on column system_role.record_created
            is 'inserted record timestamp (current datetime)';
        comment
            on column system_role.record_updated is 'record_updated record timestamp (default - current datetime)';
        comment
            on column system_role.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
        comment
            on table system_role is 'role model role table';

        RAISE
            NOTICE 'TABLE system_role created success';
    END
$$;