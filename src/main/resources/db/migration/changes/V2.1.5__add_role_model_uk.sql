SET search_path TO ${schema};

ALTER TABLE system_role_model
    ADD CONSTRAINT system_role_model_unique UNIQUE (role_system_name, permission_group_name, permission_system_name);


