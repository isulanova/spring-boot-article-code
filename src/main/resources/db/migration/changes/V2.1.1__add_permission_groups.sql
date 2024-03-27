SET
search_path TO
${schema};

INSERT INTO system_permission_group (id, name, description, alias, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NAVBAR', 'Навигационная панель', 'NAV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission_group (id, name, description, alias, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADMIN', 'Консоль администрирования', 'ADM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission_group (id, name, description, alias, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'MOBILE', 'Мобильное приложение', 'MOB', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');