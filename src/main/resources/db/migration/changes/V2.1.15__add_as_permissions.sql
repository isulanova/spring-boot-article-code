SET search_path TO ${schema};

DO
$$
    DECLARE
        nav_view_as_tab_permission                      CONSTANT varchar := 'NAV_VIEW_AS_TAB';
        nav_view_printers_as_tab_sub_item_permission    CONSTANT varchar := 'NAV_VIEW_PRINTERS_AS_TAB_SUB_ITEM';
        as_printers_edit_permission                     CONSTANT varchar := 'AS_PRINTERS_EDIT';
        as_accept_pallet_permission                     CONSTANT varchar := 'AS_ACCEPT_PALLET';
        as_display_goods_permission                     CONSTANT varchar := 'AS_DISPLAY_GOODS';

        as_receiver_role                                CONSTANT varchar := 'AS_RECEIVER';
        as_sales_floor_worker_role                      CONSTANT varchar := 'AS_SALES_FLOOR_WORKER';
        as_admin_role                                   CONSTANT varchar := 'AS_ADMIN';
        admin_role                                      CONSTANT varchar := 'ADMIN';

        navbar_group                                    CONSTANT varchar := 'NAVBAR';
        as_group                                        CONSTANT varchar := 'AS';

        row_status                                      CONSTANT varchar := 'ACTIVE';
    BEGIN
        -- create permissions
        INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
        VALUES (uuid_generate_v4(), nav_view_as_tab_permission, 'Видимость вкладки Адресное хранение',
                'Видимость вкладки Адресное хранение', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status)
        ON CONFLICT DO NOTHING;

        INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
        VALUES (uuid_generate_v4(), nav_view_printers_as_tab_sub_item_permission, 'Видимость вкладки Принтеры транзитной зоны',
                'Видимость вкладки Принтеры транзитной зоны', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status)
        ON CONFLICT DO NOTHING;

        INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
        VALUES (uuid_generate_v4(), as_printers_edit_permission, 'Управление принтерами транзитной зоны',
                'Управление принтерами транзитной зоны', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status)
        ON CONFLICT DO NOTHING;

        INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
        VALUES (uuid_generate_v4(), as_accept_pallet_permission, 'Приемка паллет',
                'Приемка паллет', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status)
        ON CONFLICT DO NOTHING;

        INSERT INTO system_permission (id, system_name, ui_name, description, record_created, record_updated, record_status)
        VALUES (uuid_generate_v4(), as_display_goods_permission, 'Выкладка товаров',
                'Выкладка товаров', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status)
        ON CONFLICT DO NOTHING;

        -- create roles
        INSERT INTO system_role (id, system_name, ui_name, description, record_created, record_updated, record_status)
        VALUES  (gen_random_uuid(), as_receiver_role, 'Приемщик', 'Приемщик в транзитной зоне', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status),
                (gen_random_uuid(), as_sales_floor_worker_role, 'Работник торгового зала', 'Работник торгового зала (РТЗ)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status),
                (gen_random_uuid(), as_admin_role, 'Администратор Адресное хранение', 'Администратор модуля Адресное хранение', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status)
        ON CONFLICT DO NOTHING;

        -- create group
        INSERT INTO system_permission_group (id, "name", description, alias, record_created, record_updated, record_status) 
        VALUES(gen_random_uuid(), as_group, 'Адресное хранение', as_group, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status)
        ON CONFLICT DO NOTHING;

        -- create role model
        INSERT INTO system_role_model (id, role_system_name, permission_group_name, permission_system_name, record_created, record_updated, record_status)
        VALUES  (gen_random_uuid(), admin_role, navbar_group, nav_view_as_tab_permission, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status),
                (gen_random_uuid(), admin_role, navbar_group, nav_view_printers_as_tab_sub_item_permission, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status),
                (gen_random_uuid(), admin_role, as_group, as_printers_edit_permission, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status),
                (gen_random_uuid(), admin_role, as_group, as_accept_pallet_permission, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status),
                (gen_random_uuid(), admin_role, as_group, as_display_goods_permission, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, row_status)
        ON CONFLICT DO NOTHING;

    END
$$;