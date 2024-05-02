SET search_path TO ${schema};

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NL_NOMENCLATURE_VIEW', 'Просмотр номенклатуры', 'Просмотр номенклатуры', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NL_NOMENCLATURE_EDIT', 'Редактирование номенклатуры', 'Редактирование номенклатуры',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NL_DEVICE_EDIT', 'Управление устройствами', 'Управление устройствами', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NL_SETTINGS_EDIT', 'Управление настройками', 'Управление настройками', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created,
                               record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADMIN', 'NL', 'NL_NOMENCLATURE_VIEW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created,
                               record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADMIN', 'NL', 'NL_NOMENCLATURE_EDIT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created,
                               record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADMIN', 'NL', 'NL_DEVICE_EDIT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created,
                               record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADMIN', 'NL', 'NL_SETTINGS_EDIT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;