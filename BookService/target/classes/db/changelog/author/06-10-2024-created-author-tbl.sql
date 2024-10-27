--liquibase formatted sql
--changeset Valkanouski:06-10-2024-created-author-tbl runOnChange:false

create table if not exists author
(
    id      bigserial
    primary key,
    name    varchar not null,
    surname varchar not null
);

comment on column author.id is 'ID автора';

comment on column author.name is 'Имя автора';

comment on column author.surname is 'Фамилия автора';

