create table if not exists  votes
(
    answer_id integer not null,
    create_at date not null,
    id serial not null,
    user_id integer not null,
    primary key (id)
);