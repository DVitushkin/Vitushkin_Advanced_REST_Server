--liquibase formatted sql

--changeset liquibase-docs:createTable-news_tags
CREATE TABLE IF NOT EXISTS public.news_tags
(
    news_id BIGINT NOT NULL
        CONSTRAINT fki06sdgpsvq2oxtharq5q1rc3x
            REFERENCES public.news,
    tag_id  BIGINT NOT NULL
        CONSTRAINT fkcwidtu2cxrh8rt0aia8lpun9f
            REFERENCES public.tag
);
