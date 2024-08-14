SET search_path TO ${schema};

-------------------------------------------------------

UPDATE system_permission
SET description = 'Управление приложением'
WHERE system_name = 'NAV_VIEW_NL_TAB';