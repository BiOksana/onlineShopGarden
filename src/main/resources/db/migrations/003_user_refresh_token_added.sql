-- liquibase formatted sql
-- changeset yevhen.kariev:003

ALTER TABLE users
    ADD COLUMN refresh_token VARCHAR(255) NULL;