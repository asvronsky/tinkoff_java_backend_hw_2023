--liquibase formatted SQL

--changeset asvronsky:02-create-link-table
create table link (
    id          bigint             generated always as identity,
    url         text                                   not null,
    updated_at  timestamp with time zone DEFAULT now() not null,

    primary key (id),
    unique      (url)
);