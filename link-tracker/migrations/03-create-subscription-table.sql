--liquibase formatted SQL

--changeset asvronsky:03-create-subscription-table
create table subscription (
    link_id bigint not null,
    chat_id bigint not null,

    primary key (link_id, chat_id),
    foreign key (link_id) references link (id) on delete cascade,
    foreign key (chat_id) references chat (chat_id) on delete cascade
);
