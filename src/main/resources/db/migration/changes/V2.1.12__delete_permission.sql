SET search_path TO ${schema};

-------------------------------------------------------

DO
$$
    DECLARE
        delete_permissions TEXT[] := ARRAY[
            'NAV_VIEW_ROLE_MODEL_ADMIN_TAB_SUB_ITEM',
            'NAV_VIEW_USERS_ADMIN_TAB_SUB_ITEM',
            'NAV_VIEW_DEVICES_NL_TAB_SUB_ITEM',
            'NAV_VIEW_SETTINGS_NL_TAB_SUB_ITEM',
            'NAV_VIEW_NOMENCLATURE_NL_TAB_SUB_ITEM'];
    BEGIN
        DELETE
        FROM system_role_model srm
        WHERE srm.permission_system_name IN (SELECT unnest(delete_permissions));

        DELETE
        FROM system_permission sp
        WHERE sp.system_name IN (SELECT unnest(delete_permissions));
    END
$$;