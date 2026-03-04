SET search_path TO ${schema};

-------------------------------------------------------

DO
$$
    DECLARE
        nl_group varchar := 'NL';
        nl_settings_view_permission varchar := 'NL_SETTINGS_VIEW';

        now timestamp = CURRENT_TIMESTAMP;
        row_status varchar := 'ACTIVE';
    BEGIN
        INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
        VALUES (uuid_generate_v4(), 'NL_SETTINGS_VIEW', 'Чтение настроек номенклатуры',
                 'Чтение настроек номенклатуры', now, now, 'ACTIVE')
        ON CONFLICT DO NOTHING;


        INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated, record_status)
        VALUES (gen_random_uuid(), 'NL_BUSINESS_ADMIN', nl_group, nl_settings_view_permission, now, now, row_status),
               (gen_random_uuid(), 'ADMIN', nl_group, nl_settings_view_permission, now, now, row_status),
               (gen_random_uuid(), 'NL_BUSINESS_ADMIN', nl_group, nl_settings_view_permission, now, now, row_status)
        ON CONFLICT DO NOTHING;
    END
$$;