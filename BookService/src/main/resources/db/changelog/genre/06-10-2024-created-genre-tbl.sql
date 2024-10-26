--liquibase formatted sql
--changeset Valkanouski:06-10-2024-created-genre-tbl runOnChange:false

create table if not exists genre
(
    id   bigserial
    primary key,
    name varchar not null
    unique
);

comment on column genre.id is 'ID жанра';

comment on column genre.name is 'Название жанра';

