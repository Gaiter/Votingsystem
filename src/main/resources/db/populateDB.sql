DELETE FROM user_roles;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM voices;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),--100000
       ('User2', 'user2@yandex.ru', '{noop}password'),--100001
       ('Admin', 'admin@gmail.com', '{noop}admin');--100002

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_USER', 100001),
       ('ROLE_USER', 100002),
       ('ROLE_ADMIN', 100002);

INSERT INTO restaurants (name)
VALUES ('Mandarin'),--100003
       ('Stargorod'),--100004
       ('Buffet');--100005

INSERT INTO voices (date, restaurant_id, user_id)
VALUES (current_date, 100003, 100000),--100006
       ('2018-09-20', 100005, 100001),--100007
       (current_date, 100004, 100002);--100008

INSERT INTO menus (name, restaurant_id, date)
VALUES ('Lunch', 100003, current_date),--100009
       ('SuperLunch', 100004, current_date),--1000010
       ('LiteLunch', 100005, '2018-09-20');--1000011

INSERT INTO dishes (name, price, menu_id)
VALUES ('Salad', 1000, 100009),--1000012
       ('Soup', 1500, 100009),--100013
       ('Meat', 2000, 100010),--100014
       ('Beer', 1000, 100010),--100015
       ('Juice', 500, 100011),--100016
       ('Pizza', 2000, 100011);--100017