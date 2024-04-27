SET search_path TO ${schema};

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES(uuid_generate_v4(), 'NL_STICKER', 'Стикеровка', 'Стикеровка', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES(uuid_generate_v4(), 'NL_WRITE_OFF', 'Списание', 'Списание', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated, record_status)
VALUES(uuid_generate_v4(), 'NL_STICKER', 'NL', 'NL_STICKER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated, record_status)
VALUES(uuid_generate_v4(), 'NL_WRITE_OFF', 'NL', 'NL_WRITE_OFF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE')
ON CONFLICT DO NOTHING;