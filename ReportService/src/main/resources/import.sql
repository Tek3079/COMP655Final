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
values (1, 1, 2, '2023-04-30', 45.0),
(1, 1, 2, '2023-04-30', 45.0),
(1, 1, 2, '2023-04-30', 45.0),
(1, 1, 2, '2023-04-30', 45.0),
(1, 1, 2, '2023-04-30', 45.0),
(1, 1, 2, '2023-04-30', 45.0),
(1, 1, 2, '2023-04-30', 45.0),
(1, 1, 2, '2023-04-30', 45.0),
(1, 1, 2, '2023-04-30', 45.0),
(1, 1, 2, '2023-04-30', 45.0);

alter sequence orders_seq restart with 11;