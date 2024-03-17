SET
    search_path TO
        ${schema};

----------------------------------------------------------------------------
DO
$$
    BEGIN
        RAISE
            NOTICE 'CREATE TABLE system_permission_group';

        drop table if exists system_permission_group cascade;

        create table system_permission_group
        (
            id             uuid         not null
                constraint system_permission_group_pkey
                    primary key,
            name           varchar(255) not null
                constraint uk_system_permission_group_name
                    unique,
            description varchar(255) not null,
            alias varchar(255) not null,
            record_created timestamp,
            record_updated timestamp,
            record_status  varchar(255)
        );

        comment
            on column system_permission_group.id is 'system_permission_group id';
        comment
            on column system_permission_group.name is 'system permission group name';
        comment
            on column system_permission_group.description is 'system permission group description';
        comment
            on column system_permission_group.alias is 'system permission group alias';
        comment
            on column system_permission_group.record_created
            is 'inserted record timestamp (current datetime)';
        comment
            on column system_permission_group.record_updated is 'record_updated record timestamp (default - current datetime)';
        comment
            on column system_permission_group.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
        comment
            on table system_permission_group is 'system permission group table';

        RAISE NOTICE 'TABLE system_permission_group created success';
    END
$$;


