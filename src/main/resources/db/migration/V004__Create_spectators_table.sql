create table spectators
(
    poll_id integer not null,
    user_id integer not null,
    primary key (poll_id, user_id)
);