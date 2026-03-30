SET search_path TO ${schema};

-------------------------------------------------------

DO
$$
    DECLARE
        as_pallet_assemble_permission   CONSTANT varchar := 'AS_PALLET_ASSEMBLE';
        admin_role                      CONSTANT varchar := 'ADMIN';
        as_group                        CONSTANT varchar := 'AS';
        row_status                      CONSTANT varchar := 'ACTIVE';
    BEGIN

        INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
        VALUES (gen_random_uuid(), as_pallet_assemble_permission, 'Сборка паллет',
                'Сборка паллет', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status)
        ON CONFLICT DO NOTHING;

        INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated, record_status)
        VALUES  (gen_random_uuid(), admin_role, as_group, as_pallet_assemble_permission, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status)
        ON CONFLICT DO NOTHING;

    END
$$;
