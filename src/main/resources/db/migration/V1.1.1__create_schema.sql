CREATE SCHEMA IF NOT EXISTS ${schema};

SET search_path TO public;
DROP EXTENSION IF EXISTS "uuid-ossp";

CREATE EXTENSION "uuid-ossp" SCHEMA public;

drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1;