--liquibase formatted sql

--changeset liquibase-docs:createTable-users
CREATE TABLE IF NOT EXISTS public.users
(
    user_id  UUID NOT NULL
        PRIMARY KEY,
    avatar   VARCHAR(255),
    email    VARCHAR(255)
        CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7
            UNIQUE,
    name     VARCHAR(255),
    password VARCHAR(255),
    role     VARCHAR(255)
);


