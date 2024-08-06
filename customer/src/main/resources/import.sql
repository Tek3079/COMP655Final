CREATE TABLE IF NOT EXISTS customer (
    id SERIAL PRIMARY KEY,
    name character varying NOT NULL,
    email character varying NOT NULL,
    amount double precision
);

INSERT INTO customer (id, name, email, balance)
VALUES (1, 'Ivy', 'Ivy@example.com', 100000),
(2, 'Jack', 'Jack@example.com', 100000),
(3, 'Alice', 'Alice@example.com', 10000),
(4, 'David', 'David@example.com', 500000),
(5, 'Hank', 'Hank@example.com', 770000);

ALTER SEQUENCE customer_seq RESTART WITH 6;