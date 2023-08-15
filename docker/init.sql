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
INSERT INTO trader (name, password)
values ('nikonov_ilia', 'nikonov_ilia');
INSERT INTO transaction (amount_given, trader_id, currency_name_given, type_id, commission)
values (
        660,
        (select id from trader where name = 'nikonov_ilia'),
        'RUB',
        (select id from transaction_type where name = 'DEPOSITING'),
        10.0
        );
INSERT INTO transaction (amount_given, trader_id, currency_name_given, type_id, commission)
values (
           50,
           (select id from trader where name = 'nikonov_ilia'),
           'RUB',
           (select id from transaction_type where name = 'DEPOSITING'),
           10.0
       );
INSERT INTO transaction (amount_given, trader_id, currency_name_received, currency_name_given, type_id, commission)
values (
           13,
           (select id from trader where name = 'nikonov_ilia'),
           'RUB',
            'USD',
           (select id from transaction_type where name = 'EXCHANGE'),
           10.0
       );
INSERT INTO transaction (amount_given, trader_id, currency_name_given, type_id, commission)
values (
           160,
           (select id from trader where name = 'nikonov_ilia'),
           'RUB',
           (select id from transaction_type where name = 'DEPOSITING'),
           10.0
       );
INSERT INTO transaction (amount_given, trader_id, currency_name_given, type_id, commission)
values (
           170,
           (select id from trader where name = 'nikonov_ilia'),
           'USD',
           (select id from transaction_type where name = 'WITHDRAWAL'),
           13.0
       );
INSERT INTO transaction (amount_given, trader_id, currency_name_given, type_id, commission)
values (
           110,
           (select id from trader where name = 'nikonov_ilia'),
           'USD',
           (select id from transaction_type where name = 'DEPOSITING'),
           11.0
       );
INSERT INTO transaction (amount_given, trader_id, currency_name_given, type_id, commission)
values (
           600,
           (select id from trader where name = 'nikonov_ilia'),
           'RUB',
           (select id from transaction_type where name = 'WITHDRAWAL'),
           15.0
       );
INSERT INTO transaction (amount_given, trader_id, currency_name_received, currency_name_given, type_id, commission)
values (
           170,
           (select id from trader where name = 'nikonov_ilia'),
           'RUB',
           'USD',
           (select id from transaction_type where name = 'EXCHANGE'),
           10.0
       );
INSERT INTO transaction (amount_given, trader_id, currency_name_received, currency_name_given, type_id, commission)
values (
           110,
           (select id from trader where name = 'nikonov_ilia'),
           'RUB',
           'USD',
           (select id from transaction_type where name = 'EXCHANGE'),
           10.0
       );
