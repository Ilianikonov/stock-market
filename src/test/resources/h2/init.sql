drop table if exists transaction;
drop table if exists trader_to_role;
drop table if exists role;
drop table if exists transaction_type;
drop table if exists trader;

create table trader
(
    id       serial primary key,
    name     varchar(12) not null unique,
    password varchar(30) not null,
    creation_date timestamp not null default CURRENT_TIMESTAMP,
    enabled boolean not null default true
);

create table role
(
    id serial primary key,
    name varchar not null
);

create table trader_to_role
(
    id serial primary key,
    trader_id int references trader (id),
    role_id int references role (id)
);

create index trader_id_index on trader(id);
create index trader_name_index on trader(name);

create table transaction_type
(
    id serial primary key,
    name varchar(10) not null
);

create table transaction
(
    id                 serial primary key,
    currency_name_received varchar(10),
    currency_name_given   varchar(10),
    amount_received           double precision,
    amount_given             double precision,
    trader_id          int references trader (id),
    type_id            int references transaction_type (id),
    commission         double precision not null
);
create index transaction_trader_id_index on transaction(trader_id);
create index transaction_id_index on transaction(id);

INSERT INTO role (name)
values ('ADMIN');
INSERT INTO role (name)
values ('USER');
INSERT INTO transaction_type (name)
values ('DEPOSITING');
INSERT INTO transaction_type (name)
values ('WITHDRAWAL');
INSERT INTO transaction_type (name)
values ('EXCHANGE');
