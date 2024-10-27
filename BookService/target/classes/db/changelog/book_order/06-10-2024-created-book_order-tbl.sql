--liquibase formatted sql
--changeset Valkanouski:06-10-2024-created-author-tbl runOnChange:false

create table book_order
(
    id         bigserial
        primary key,
    book_id    bigint            not null
        constraint book_order_book_id_fk
            references book,
    user_id    bigint            not null
        constraint book_order_users_id_fk
            references users,
    quantity   integer default 1 not null,
    status     varchar           not null,
    order_date date              not null,
    created    timestamp         not null,
    changed    timestamp         not null
);




