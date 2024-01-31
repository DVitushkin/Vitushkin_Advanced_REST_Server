--liquibase formatted sql

--changeset liquibase-docs:createTable-log_event
CREATE TABLE IF NOT EXISTS public.log_event
(
    id            BIGINT NOT NULL
        PRIMARY KEY,
    country       VARCHAR(255),
    exception     VARCHAR(255),
    http_method   VARCHAR(255),
    http_protocol VARCHAR(255),
    java_method   VARCHAR(255),
    parameters    VARCHAR(255),
    request_uri   VARCHAR(255),
    status_code   VARCHAR(255),
    timestamp     TIMESTAMP(6)
);
