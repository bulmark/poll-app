create table if not exists  answers
(
    id serial not null,
    question_id integer not null,
    text varchar(255) not null,
    primary key (id)
);