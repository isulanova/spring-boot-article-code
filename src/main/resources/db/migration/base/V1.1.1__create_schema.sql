CREATE SCHEMA IF NOT EXISTS ${schema};

SET search_path TO ${schema};

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1;