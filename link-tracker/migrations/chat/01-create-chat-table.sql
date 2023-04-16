--liquibase formatted SQL

--changeset asvronsky:01-create-chat-table
create table chat (
    chat_id bigint not null,

    primary key (chat_id)
);
