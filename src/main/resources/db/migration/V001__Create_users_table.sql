create table if not exists users
(
    id serial not null,
    email varchar(50) unique,
    username varchar(50) unique,
    password varchar(64),
    role varchar(255) check (role in ('USER','MODERATOR','ADMIN')),
    primary key (id)
);