SET
search_path TO
${schema};

DO
$$
BEGIN
ALTER TABLE system_permission_group
    RENAME COLUMN name TO system_name;
EXCEPTION
        WHEN undefined_column THEN
END;
$$;


create table if not exists system_role_model_temp
(
    id                  uuid not null
    primary key,
    role_system_name             varchar(255)
    constraint fk_system_role_model_role_
    references system_role (system_name),
    permission_group_system_name varchar(255)
    constraint fk_system_role_model_permission_group
    references system_permission_group (system_name),
    permission_system_name       varchar(255)
    constraint fk_system_role_model_permission
    references system_permission (system_name),
    record_created      timestamp,
    record_updated      timestamp,
    record_status       varchar(255)
    );

INSERT INTO system_role_model_temp
SELECT
    srm.id,
    (SELECT system_name FROM system_role sr WHERE sr.id = srm.role_id),
    (SELECT system_name FROM system_permission_group spg WHERE spg.id = srm.permission_group_id),
    (SELECT system_name FROM system_permission sp WHERE sp.id = srm.permission_id),
    srm.record_created,
    srm.record_updated,
    srm.record_status
FROM system_role_model srm ON CONFLICT DO NOTHING;

DROP TABLE IF EXISTS system_role_model;

ALTER TABLE IF EXISTS system_role_model_temp rename to system_role_model;