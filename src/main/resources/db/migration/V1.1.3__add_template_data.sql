SET
search_path TO
${schema};

INSERT INTO template_data (id, date_type, enum_type, id_type, string_type, record_created, record_updated,
                           record_status)
VALUES (public.uuid_generate_v4(), CURRENT_TIMESTAMP, 'VALUE1', public.uuid_generate_v4(), 'some_text_1',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO template_data (id, date_type, enum_type, id_type, string_type, record_created, record_updated,
                           record_status)
VALUES (public.uuid_generate_v4(), CURRENT_TIMESTAMP, 'VALUE2', public.uuid_generate_v4(), 'some_text_2',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');

INSERT INTO template_data (id, date_type, enum_type, id_type, string_type, record_created, record_updated,
                           record_status)
VALUES (public.uuid_generate_v4(), CURRENT_TIMESTAMP, 'VALUE3', public.uuid_generate_v4(), 'some_text_3',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE');