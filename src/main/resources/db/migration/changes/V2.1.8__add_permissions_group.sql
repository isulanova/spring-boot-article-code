SET
    search_path TO
        ${schema};

INSERT INTO system_permission_group (id, name, description, alias, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'PDCT', '#БезОстатка ТСД', 'PDCT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NL_STICKER', 'PDCT', 'NL_STICKER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NL_WRITE_OFF', 'PDCT', 'NL_WRITE_OFF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

ALTER TABLE system_role_model
    DROP CONSTRAINT fk_system_role_model_permission;
ALTER TABLE system_role_model
    ADD CONSTRAINT fk_system_role_model_permission
        FOREIGN KEY (permission_system_name) REFERENCES system_permission (system_name)
            ON UPDATE CASCADE;

UPDATE system_permission
SET system_name ='PDCT_STICKER'
WHERE system_name = 'NL_STICKER';

UPDATE system_permission
SET system_name ='PDCT_WRITE_OFF'
WHERE system_name = 'NL_WRITE_OFF';

DELETE FROM system_role_model
WHERE permission_system_name IN ('PDCT_STICKER', 'PDCT_WRITE_OFF') AND permission_group_name <> 'PDCT';
