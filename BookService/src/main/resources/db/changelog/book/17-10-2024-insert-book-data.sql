--liquibase formatted sql
--changeset Valkanouski:17-10-2024-insert-author-data runOnChange:false

INSERT INTO book (id, name, author_id, genre_id, created, changed) VALUES (2, 'Евгений Онегин', 2, 2, '2024-10-06 18:16:41.000000', '2024-10-06 18:16:41.000000');
INSERT INTO book (id, name, author_id, genre_id, created, changed) VALUES (3, 'Преступление и наказание', 3, 2, '2024-10-06 18:16:43.000000', '2024-10-06 18:16:43.000000');
INSERT INTO book (id, name, author_id, genre_id, created, changed) VALUES (4, 'Война и мир', 4, 2, '2024-10-06 18:16:44.000000', '2024-10-06 18:16:45.000000');
INSERT INTO book (id, name, author_id, genre_id, created, changed) VALUES (5, 'Герой нашего времени', 5, 2, '2024-10-06 18:16:46.000000', '2024-10-06 18:16:46.000000');
INSERT INTO book (id, name, author_id, genre_id, created, changed) VALUES (6, 'Мертвые души', 6, 3, '2024-10-06 18:16:47.000000', '2024-10-06 18:16:48.000000');
INSERT INTO book (id, name, author_id, genre_id, created, changed) VALUES (7, 'Горе от ума', 7, 4, '2024-10-06 18:16:49.000000', '2024-10-06 18:16:50.000000');
INSERT INTO book (id, name, author_id, genre_id, created, changed) VALUES (8, 'Отцы и дети', 8, 2, '2024-10-06 18:16:51.000000', '2024-10-06 18:16:51.000000');
INSERT INTO book (id, name, author_id, genre_id, created, changed) VALUES (1, 'Мастер и Маргарита', 1, 2, '2024-10-06 18:16:39.000000', '2024-10-17 20:54:31.514945');

