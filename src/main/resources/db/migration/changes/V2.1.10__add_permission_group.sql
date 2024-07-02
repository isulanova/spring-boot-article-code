SET search_path TO ${schema};

-------------------------------------------------------

DO
$$
    DECLARE
        now timestamp = CURRENT_TIMESTAMP;
        row_status varchar := 'ACTIVE';
        pdct_group varchar := 'PDCT';
        pdct_description varchar := '#БезОстатка ТСД';
        sticker_permission varchar := 'NL_STICKER';
        sticker_role varchar := 'NL_STICKER';
        sticker_description varchar := 'Стикеровка';
        write_off_permission varchar := 'NL_WRITE_OFF';
        write_off_role varchar := 'NL_WRITE_OFF';
        write_off_description varchar := 'Списание';
    BEGIN
        IF NOT EXISTS(SELECT * FROM system_permission_group WHERE name = pdct_group) THEN
            INSERT INTO system_permission_group (id, name, description, alias, record_created, record_updated, record_status)
            VALUES (uuid_generate_v4(), pdct_group, pdct_description, pdct_group, now, now, row_status);

            IF NOT EXISTS(SELECT * FROM system_permission WHERE system_name = sticker_permission) THEN
                INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
                VALUES (uuid_generate_v4(), sticker_permission, sticker_description, sticker_description, now, now, row_status);
            END IF;

            IF NOT EXISTS(SELECT * FROM system_permission WHERE system_name = write_off_permission) THEN
                INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
                VALUES (uuid_generate_v4(), write_off_permission, write_off_description, write_off_description, now, now, row_status);
            END IF;

            IF NOT EXISTS(SELECT * FROM system_role WHERE system_name = sticker_role) THEN
                INSERT INTO system_role (id, system_name, ui_name, description, record_created, record_updated, record_status)
                VALUES (uuid_generate_v4(), sticker_role, sticker_description, sticker_description, now, now, row_status);
            END IF;

            IF NOT EXISTS(SELECT * FROM system_role WHERE system_name = write_off_role) THEN
                INSERT INTO system_role (id, system_name, ui_name, description, record_created, record_updated, record_status)
                VALUES (uuid_generate_v4(), write_off_role, write_off_description, write_off_description, now, now, row_status);
            END IF;

            DELETE FROM system_role_model WHERE role_system_name IN (sticker_role, write_off_role);

            INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated, record_status)
            VALUES (uuid_generate_v4(), sticker_role, pdct_group, sticker_permission, now, now, row_status);

            INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated, record_status)
            VALUES (uuid_generate_v4(), write_off_role, pdct_group, write_off_permission, now, now, row_status);
        END IF;
    END
$$;