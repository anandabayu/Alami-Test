/*
 * File: postgres.sql
 * Project: sql
 * File Created: Sunday, 7th November 2021 9:12:09 pm
 * Author: Ananda Yudhistira (anandabayu12@gmail.com)
 * -----
 * Last Modified: Sunday, 7th November 2021 9:12:12 pm
 * Modified By: Ananda Yudhistira (anandabayu12@gmail.com>)
 * -----
 * Copyright 2021 Ananda Yudhistira, -
 */
create table users (
	id serial primary key,
	name varchar(50) not null,
	address text not null,
	born_date date not null
);

insert into
    users (name, address, born_date)
values
    ('Wawan Setiawan', 'Kompleks Asia Serasi No 100', '1990-01-10'),
    ('Teguh Sudibyantoro', 'Jalan Pemekaran No 99', '1991-02-10'),
    ('Joko Widodo', 'Dusun Pisang RT 10 RW 20', '1992-03-10');

-- create type t_type as enum('MENYIMPAN', 'MEMINJAM', 'MENGEMBALIKAN', 'MENGAMBIL');
CREATE TABLE transactions (
    id serial primary key,
    transaction_date timestamp DEFAULT CURRENT_TIMESTAMP,
    user_id serial not null,
    nominal int not null,
    type int not null,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO
    transactions (transaction_date, user_id, nominal, type)
VALUES
    ('2020-08-17', 1, 1000000, 1),
    ('2020-08-18', 2, 5000000, 1),
    ('2020-09-30', 3, 2000000, 2),
    ('2020-11-10', 3, 1000000, 3),
    ('2020-12-01', 1, 5000000, 1),
    ('2020-12-01', 1, 2000000, 4);