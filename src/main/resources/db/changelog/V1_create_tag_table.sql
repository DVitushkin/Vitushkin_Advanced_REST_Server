--liquibase formatted sql

--changeset liquibase-docs:createTable-tag
CREATE TABLE IF NOT EXISTS public.tag
(
    tag_id BIGINT NOT NULL
        PRIMARY KEY,
    title  VARCHAR(255)
);


