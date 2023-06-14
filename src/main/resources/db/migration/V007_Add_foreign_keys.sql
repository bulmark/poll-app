alter table if exists answers
    add constraint answers_questions_fk
    foreign key (question_id) references questions;

alter table if exists polls
    add constraint polls_users_fk
    foreign key (owner_id) references users;

alter table if exists questions
    add constraint questions_polls_fk
    foreign key (poll_id) references polls;

alter table if exists spectators
    add constraint spectators_users_fk
    foreign key (user_id) references users;

alter table if exists spectators
    add constraint spectators_polls_fk
    foreign key (poll_id) references polls;

alter table if exists votes
    add constraint votes_answers_fk
    foreign key (answer_id) references answers;

alter table if exists votes
    add constraint votes_users_fk
    foreign key (user_id) references users;