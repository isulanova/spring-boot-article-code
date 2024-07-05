SET search_path TO ${schema}, public;

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADM_DELETE_USERS', 'Удаление пользователя',
        'Удаление пользователя', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name,
                               record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADMIN', 'ADMIN', 'ADM_DELETE_USERS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;
