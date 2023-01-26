SET
search_path TO
${schema};

DO
$$
BEGIN
        RAISE
NOTICE 'CREATE TABLE template_data';

drop table if exists template_data cascade;

create table template_data
(
    id             uuid not null
        primary key,
    date_type      timestamp,
    enum_type      varchar(255),
    id_type        uuid,
    string_type    varchar(255),
    record_created timestamp,
    record_updated timestamp,
    record_status  varchar(255)
);

RAISE
NOTICE 'TABLE template_data CREATED SUCCESS';
END $$;

