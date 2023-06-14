create table questions
(
    id serial not null,
    multiple boolean not null,
    poll_id integer not null,
    text varchar(255) not null,
    primary key (id)
);