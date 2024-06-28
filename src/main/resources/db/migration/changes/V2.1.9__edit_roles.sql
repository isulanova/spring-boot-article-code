SET
search_path TO
${schema};

-------------------------------------------------------
DO
$$
    DECLARE
now timestamp;
BEGIN
        now
:= CURRENT_TIMESTAMP;

DELETE
FROM system_role_model
WHERE role_system_name = 'NL_ADMIN';

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name,
                               record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), 'NL_ADMIN', 'NAVBAR', 'NAV_VIEW_ADMIN_TAB', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'NAVBAR', 'NAV_VIEW_ROLE_MODEL_ADMIN_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'ADMIN', 'ADM_EDIT_ROLE_MODEL', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'NAVBAR', 'NAV_VIEW_USERS_ADMIN_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'ADMIN', 'ADM_EDIT_USERS', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'NAVBAR', 'NAV_VIEW_NL_TAB', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'NAVBAR', 'NAV_VIEW_DEVICES_NL_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'NL', 'NL_DEVICE_EDIT', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'NAVBAR', 'NAV_VIEW_SETTINGS_NL_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'NL', 'NL_SETTINGS_EDIT', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'NAVBAR', 'NAV_VIEW_NOMENCLATURE_NL_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_ADMIN', 'NL', 'NL_NOMENCLATURE_VIEW', now, now, 'ACTIVE');

DELETE
FROM system_role_model
WHERE role_system_name = 'NL_BUSINESS_ADMIN';
DELETE
FROM system_role
WHERE system_name = 'NL_BUSINESS_ADMIN';

INSERT INTO system_role (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NL_BUSINESS_ADMIN', 'Бизнес-администратор #БезОстатка',
        'Бизнес-администратор #БезОстатка', now, now, 'ACTIVE');

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created,
                               record_updated,
                               record_status)
VALUES (uuid_generate_v4(), 'NL_BUSINESS_ADMIN', 'NAVBAR', 'NAV_VIEW_NL_TAB', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_BUSINESS_ADMIN', 'NAVBAR', 'NAV_VIEW_NOMENCLATURE_NL_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_BUSINESS_ADMIN', 'NL', 'NL_NOMENCLATURE_VIEW', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'NL_BUSINESS_ADMIN', 'NL', 'NL_NOMENCLATURE_EDIT', now, now, 'ACTIVE');

DELETE
FROM system_role_model
WHERE role_system_name = 'ADMIN';

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name,
                               record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), 'ADMIN', 'NAVBAR', 'NAV_VIEW_ADMIN_TAB', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'NAVBAR', 'NAV_VIEW_ROLE_MODEL_ADMIN_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'ADMIN', 'ADM_EDIT_ROLE_MODEL', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'NAVBAR', 'NAV_VIEW_USERS_ADMIN_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'ADMIN', 'ADM_EDIT_USERS', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'NAVBAR', 'NAV_VIEW_NL_TAB', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'NAVBAR', 'NAV_VIEW_DEVICES_NL_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'NL', 'NL_DEVICE_EDIT', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'NAVBAR', 'NAV_VIEW_SETTINGS_NL_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'NL', 'NL_SETTINGS_EDIT', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'NAVBAR', 'NAV_VIEW_NOMENCLATURE_NL_TAB_SUB_ITEM', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'NL', 'NL_NOMENCLATURE_VIEW', now, now, 'ACTIVE'),
       (uuid_generate_v4(), 'ADMIN', 'NL', 'NL_NOMENCLATURE_EDIT', now, now, 'ACTIVE');

DELETE
FROM system_role_model
WHERE role_system_name = 'NL_STICKER';

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name,
                               record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), 'NL_STICKER', 'NL', 'NL_STICKER', now, now, 'ACTIVE');

DELETE
FROM system_role_model
WHERE role_system_name = 'NL_WRITE_OFF';

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name,
                               record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), 'NL_WRITE_OFF', 'NL', 'NL_WRITE_OFF', now, now, 'ACTIVE');
END
$$;