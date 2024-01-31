--liquibase formatted sql

--changeset liquibase-docs:createTable-news
CREATE TABLE IF NOT EXISTS public.news
(
    news_id     BIGINT NOT NULL
        PRIMARY KEY,
    description VARCHAR(255),
    image       VARCHAR(255),
    title       VARCHAR(255),
    user_id     UUID NOT NULL
        CONSTRAINT fki09n75txtudw1kawj5o7i8xag
            REFERENCES public.users
);
