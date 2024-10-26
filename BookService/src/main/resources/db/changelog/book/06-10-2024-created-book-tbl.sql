--liquibase formatted sql
--changeset Valkanouski:06-10-2024-created-book-tbl runOnChange:false

create table if not exists book
(
    id        bigserial
    primary key,
    name      varchar   not null,
    author_id bigint    not null
    constraint book_author_id_fk
    references author,
    genre_id  bigint    not null
    constraint book_genre_id_fk
    references genre,
    created   timestamp not null,
    changed   timestamp not null
);

comment on column book.id is 'ID книги';

comment on column book.name is 'Название книги';

comment on column book.author_id is 'ID автора';

comment on column book.genre_id is 'ID жанра';