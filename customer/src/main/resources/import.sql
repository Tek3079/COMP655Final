CREATE TABLE IF NOT EXISTS customer (
    id SERIAL PRIMARY KEY,
    name character varying NOT NULL,
    email character varying NOT NULL,
    amount double precision
);

INSERT INTO customer (id, name, email, balance)
VALUES (1, 'Ivy', 'Ivy@example.com', 100),
(2, 'Jack', 'Jack@example.com', 10),
(3, 'Alice', 'Alice@example.com', 1),
(4, 'David', 'David@example.com', 50),
(5, 'Hank', 'Hank@example.com', 77);

ALTER SEQUENCE customer_seq RESTART WITH 6;