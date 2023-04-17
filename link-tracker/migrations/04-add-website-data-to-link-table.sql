--liquibase formatted SQL

--changeset asvronsky:04-add-website-data-to-link-table
alter table link
    add column  website_data   jsonb;

