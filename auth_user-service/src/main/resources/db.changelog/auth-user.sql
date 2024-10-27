--liquibase formatted sql
--changeset Valeriya:24-10-2024-created-author-tbl runOnChange:false

CREATE TABLE users (
                       id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       username VARCHAR(20) UNIQUE NOT NULL,
                       birth_date DATE,
                       firstname VARCHAR(20),
                       lastname VARCHAR(20),
                       password VARCHAR(25) NOT NULL
);