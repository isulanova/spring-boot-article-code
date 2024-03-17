SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
BEGIN
            RAISE
NOTICE 'CREATE TABLE system_module';

drop table if exists system_module cascade;

create table system_module
(
    id             uuid         not null
        primary key,
    system_name    varchar(255) not null
        constraint uk_system_module
            unique,
    label          varchar(255),
    record_created timestamp,
    record_updated timestamp,
    record_status  varchar(255)
);

comment
on column system_module.id is 'role module id';
comment
on column system_module.system_name is 'role module name';
comment
on column system_module.label is 'role module label';
comment
on column system_module.record_created
            is 'inserted record timestamp (current datetime)';
comment
on column system_module.record_updated is 'record_updated record timestamp (default - current datetime)';
comment
on column system_module.record_status is 'record record_status (ACTIVE, NOT_ACTIVE, DELETED)';
comment
on table system_module is 'role module';

RAISE
NOTICE 'TABLE system_module created success';
END
$$;