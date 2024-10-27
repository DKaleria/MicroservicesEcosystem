--liquibase formatted sql
--changeset Valkanouski:17-10-2024-insert-genre-data runOnChange:false

INSERT INTO genre (id, name) VALUES (1, 'фэнтези');
INSERT INTO genre (id, name) VALUES (2, 'роман');
INSERT INTO genre (id, name) VALUES (3, 'сатира');
INSERT INTO genre (id, name) VALUES (4, 'комедия');
