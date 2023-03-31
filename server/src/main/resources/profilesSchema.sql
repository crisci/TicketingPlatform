create table if not exists profiles
(
    id      serial primary key,
    name    varchar(255),
    surname varchar(255),
    email   varchar(255) unique
);