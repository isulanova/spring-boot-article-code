SET
search_path TO
${schema};

----------------------------------------------------------------------------
DO
$$
BEGIN
RAISE NOTICE 'CREATE TABLE system_user_role';

create table if not exists system_user_role
(
    user_id uuid not null
        constraint fk_system_user_role_user_id
            references system_usr,
    role_id uuid not null
        constraint fk_system_user_role_role_id
            references system_role,
    primary key (user_id, role_id)
);

comment on column system_user_role.user_id is 'user id in system_usr';
comment on column system_user_role.role_id is 'role id in system_role';

RAISE NOTICE 'TABLE system_user_role created success';
END
$$;
