SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
    BEGIN
            RAISE
                NOTICE 'CREATE TABLE system_usr';

drop table if exists system_usr cascade;

create table system_usr
(
    keycloak_id uuid not null
        constraint system_usr_pkey
            primary key,
    user_name varchar(255),
    record_created timestamp,
    record_updated timestamp,
    record_status varchar(255)
);

        comment
on column system_usr.user_name is 'username';
        comment
on column system_usr.keycloak_id is 'user id in keycloak';
        comment
on column system_usr.record_created
            is 'inserted record timestamp (current datetime)';
        comment
on column system_usr.record_updated is 'record_updated record timestamp (default - current datetime)';
        comment
on column system_usr.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
        comment
on table system_usr is 'user table';

        RAISE
NOTICE 'TABLE system_usr created success';
END
$$;
