SET search_path TO ${schema};

-------------------------------------------------------

DO
$$
    DECLARE
        mobile_role varchar := 'NL_MOBILE_GROUP';
        mobile_role_description varchar := 'Мобильная группа';

        user_management_role varchar := 'NL_USER_MANAGEMENT';
        user_management_role_description varchar := 'Управление пользователями';

        nl_group varchar := 'NL';
        admin_group varchar := 'ADMIN';
        navbar_group varchar := 'NAVBAR';

        now timestamp = CURRENT_TIMESTAMP;
        row_status varchar := 'ACTIVE';
    BEGIN
        INSERT INTO system_role (id, system_name, ui_name, description, record_created, record_updated, record_status)
        VALUES(gen_random_uuid(), mobile_role, mobile_role_description, mobile_role_description, now, now, row_status),
              (gen_random_uuid(), user_management_role, user_management_role_description, user_management_role_description, now, now, row_status);

        INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated, record_status)
        VALUES (gen_random_uuid(), user_management_role, navbar_group, 'NAV_VIEW_ADMIN_TAB', now, now, row_status),
               (gen_random_uuid(), user_management_role, admin_group, 'ADM_DELETE_USERS', now, now, row_status),
               (gen_random_uuid(), user_management_role, admin_group, 'ADM_EDIT_USERS', now, now, row_status),
               (gen_random_uuid(), mobile_role, navbar_group, 'NAV_VIEW_NL_TAB', now, now, row_status),
               (gen_random_uuid(), mobile_role, nl_group, 'NL_DEVICE_EDIT', now, now, row_status);
    END
$$;