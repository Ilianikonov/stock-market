drop table if exists trader;
drop table if exists balance;

create table trader
(
    id       serial primary key,
    name     varchar(12) not null unique,
    password varchar(30) not null
);

create table balance
(
    id            serial primary key,
    currency_name varchar(10)      not null,
    amount        double precision not null,
    trader_id     int references trader (id)
);

INSERT INTO balance (amount, trader_id, currency_name)
values (100, 1, "RUB");
INSERT INTO tracer (name, password)
values ("nikonov_ilia", "nikonov_ilia");