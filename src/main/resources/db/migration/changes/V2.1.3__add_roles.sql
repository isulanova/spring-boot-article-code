SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
BEGIN
            RAISE
NOTICE 'INSERT ROLES to table system_role';

INSERT INTO system_role (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'ADMIN', 'Супер администратор',
        'Супер администратор', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NL_ADMIN', 'Администратор #БезОстатка',
        'Администратор #БезОстатка', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NL_STICKER', 'Стикеровка',
        'Стикеровка', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO system_role (id, system_name, ui_name, description, record_created, record_updated, record_status)
VALUES (uuid_generate_v4(), 'NL_WRITE_OFF', 'Списание',
        'Списание', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

RAISE
NOTICE 'INSERT ROLES to table system_role success';
END
$$;