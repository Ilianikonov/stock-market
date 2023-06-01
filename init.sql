drop table if exists user;
drop table if exists balance;

create table user(
   id serial primary key,
   name varchar(12) not null unique,
   password varchar(30) not null
);

create table balance(
    id serial primary key,
    currency_name varchar(10) not null,
    amount double precision not null
);