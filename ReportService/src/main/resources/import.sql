-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
--Order (id, customer_id, product_id, time, amount)
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    time TIMESTAMP NOT NULL,
    amount DOUBLE PRECISION
);
insert into orders (id, customer_id, product_id, time, amount)
values (1, 1, 2, '2023-04-30 12:01:01', 45.0),
(2, 1, 2, '2023-04-30 12:01:01', 45.0),
(3, 1, 2, '2023-04-30 12:01:01', 45.0),
(4, 1, 2, '2023-04-30 12:01:01', 45.0),
(5, 1, 2, '2023-04-30 12:01:01', 45.0),
(6, 1, 2, '2023-04-30 12:01:01', 45.0),
(7, 1, 2, '2023-04-30 12:01:01', 45.0),
(8, 1, 2, '2023-04-30 12:01:01', 45.0),
(9, 1, 2, '2023-04-30 12:01:01', 45.0),
(10, 1, 2, '2023-04-30 12:01:01', 45.0);

alter sequence orders_seq restart with 11;