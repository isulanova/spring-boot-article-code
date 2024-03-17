SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
BEGIN
            RAISE
NOTICE 'CREATE TABLE system_function';

drop table if exists system_function cascade;

create table system_function
(
    id             uuid not null
        primary key,
    record_created timestamp,
    record_status  varchar(255),
    record_updated timestamp,
    name           varchar(255)
);

comment
on column system_function.id is 'role function id';
comment
on column system_function.name is 'role function name';
comment
on column system_function.record_created
            is 'inserted record timestamp (current datetime)';
comment
on column system_function.record_updated is 'record_updated record timestamp (default - current datetime)';
comment
on column system_function.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
comment
on table system_function is 'role function';

RAISE
NOTICE 'TABLE system_function created success';
END
$$;