SET
search_path TO
${schema};

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NAV_VIEW_ADMIN_TAB', 'Видимость вкладки (Администрирование)',
        'Видимость вкладки (Администрирование)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NAV_VIEW_ROLE_MODEL_ADMIN_TAB_SUB_ITEM', 'Видимость пункта - "Ролевая модель"',
        'Видимость пункта - "Ролевая модель"', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NAV_VIEW_USERS_ADMIN_TAB_SUB_ITEM', 'Видимость пункта - "Пользователи"',
        'Видимость пункта - "Пользователи"', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NAV_VIEW_NL_TAB', 'Видимость вкладки (#БезОстатка)',
        'Управление пользователями', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NAV_VIEW_DEVICES_NL_TAB_SUB_ITEM', 'Видимость пункта - "Устройства"',
        'Видимость пункта - "Устройства"', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NAV_VIEW_SETTINGS_NL_TAB_SUB_ITEM', 'Видимость пункта - "Настройки"',
        'Видимость пункта - "Настройки"', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NAV_VIEW_NOMENCLATURE_NL_TAB_SUB_ITEM', 'Видимость пункта - "Номенклатура и скидки"',
        'Видимость пункта - "Номенклатура и скидки"', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADM_EDIT_ROLE_MODEL', 'Управление ролевой моделью',
        'Управление ролевой моделью', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADM_EDIT_USERS', 'Управление пользователями',
        'Управление пользователями', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');