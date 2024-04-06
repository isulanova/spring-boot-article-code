SET
search_path TO
${schema};

----------------------------------------------------------------------------

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), (SELECT system_name FROM system_role where system_name = 'ADMIN'),
        (SELECT name FROM system_permission_group where name = 'NAVBAR'),
        (SELECT system_name from system_permission where system_name = 'NAV_VIEW_ADMIN_TAB'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), (SELECT system_name FROM system_role where system_name = 'ADMIN'),
        (SELECT name FROM system_permission_group where name = 'NAVBAR'),
        (SELECT system_name from system_permission where system_name = 'NAV_VIEW_ROLE_MODEL_ADMIN_TAB_SUB_ITEM'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), (SELECT system_name FROM system_role where system_name = 'ADMIN'),
        (SELECT name FROM system_permission_group where name = 'NAVBAR'),
        (SELECT system_name from system_permission where system_name = 'NAV_VIEW_USERS_ADMIN_TAB_SUB_ITEM'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), (SELECT system_name FROM system_role where system_name = 'ADMIN'),
        (SELECT name FROM system_permission_group where name = 'NAVBAR'),
        (SELECT system_name from system_permission where system_name = 'NAV_VIEW_NL_TAB'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), (SELECT system_name FROM system_role where system_name = 'ADMIN'),
        (SELECT name FROM system_permission_group where name = 'NAVBAR'),
        (SELECT system_name from system_permission where system_name = 'NAV_VIEW_SETTINGS_NL_TAB_SUB_ITEM'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), (SELECT system_name FROM system_role where system_name = 'ADMIN'),
        (SELECT name FROM system_permission_group where name = 'NAVBAR'),
        (SELECT system_name from system_permission where system_name = 'NAV_VIEW_NOMENCLATURE_NL_TAB_SUB_ITEM'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), (SELECT system_name FROM system_role where system_name = 'ADMIN'),
        (SELECT name FROM system_permission_group where name = 'ADMIN'),
        (SELECT system_name from system_permission where system_name = 'ADM_EDIT_ROLE_MODEL'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated,
                               record_status)
VALUES (uuid_generate_v4(), (SELECT system_name FROM system_role where system_name = 'ADMIN'),
        (SELECT name FROM system_permission_group where name = 'ADMIN'),
        (SELECT system_name from system_permission where system_name = 'ADM_EDIT_USERS'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');