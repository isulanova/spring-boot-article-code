SET
search_path TO
${schema};

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NAV_ADMIN', 'Видимость вкладки (Администрирование)',
        'Видимость вкладки (Администрирование)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADM_GENERAL_SETTINGS', 'Редактирование общих настроек',
        'Редактирование общих настроек', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADM_PCDT_EDIT', 'Управление ТСД',
        'Управление ТСД', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADM_USER_EDIT', 'Управление пользователями',
        'Управление пользователями', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADM_NOMENCLATURE_EDIT', 'Управление номенклатурой',
        'Управление номенклатурой', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');