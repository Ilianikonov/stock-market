drop table if exists transaction;
drop table if exists trader;
drop table if exists transaction_type;

create table trader
(
    id       serial primary key,
    name     varchar(12) not null unique,
    password varchar(30) not null
);

create table transaction_type
(
    id   serial primary key,
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

INSERT INTO transaction_type (name)
values ('DEPOSITING');
INSERT INTO transaction_type (name)
values ('WITHDRAWAL');
INSERT INTO transaction_type (name)
values ('EXCHANGE');
