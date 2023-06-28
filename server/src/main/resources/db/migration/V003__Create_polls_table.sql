create table if not exists polls
(
    create_at date not null,
    id serial not null,
    owner_id integer not null,
    period interval,
    up_to_date date not null,
    text varchar(255) not null,
    primary key (id)
);