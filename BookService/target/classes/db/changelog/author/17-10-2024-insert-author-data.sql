--liquibase formatted sql
--changeset Valkanouski:17-10-2024-insert-author-data runOnChange:false

INSERT INTO author (id, name, surname) VALUES (1, 'Михаил', 'Булгаков');
INSERT INTO author (id, name, surname) VALUES (2, 'Александр', 'Пушкин');
INSERT INTO author (id, name, surname) VALUES (3, 'Федор', 'Достоевский');
INSERT INTO author (id, name, surname) VALUES (4, 'Лев', 'Толстой');
INSERT INTO author (id, name, surname) VALUES (5, 'Михаил', 'Лермонтов');
INSERT INTO author (id, name, surname) VALUES (6, 'Николай', 'Гоголь');
INSERT INTO author (id, name, surname) VALUES (7, 'Александр', 'Грибоедов');
INSERT INTO author (id, name, surname) VALUES (8, 'Иван', 'Тургенев');
INSERT INTO author (id, name, surname) VALUES (9, 'Борис', 'Пастернак');
INSERT INTO author (id, name, surname) VALUES (10, 'Александр', 'Дюма');
