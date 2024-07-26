CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    quantity INTEGER NOT NULL,
    price DOUBLE PRECISION NOT NULL,
);

insert into products(id, name, quantity, price)
values (1, 'laptop', 2, 1500.50),
(2, 'iPhone', 4, 2000.00),
(3, 'tv', 5, 500.0),
(4, 'charger', 6, 700.50),
(5, 'iPod', 1, 400.0),
(6, 'kindle', 7, 2300.0),
(7, 'mouse', 5, 800.50),
(8, 'keyboard', 23, 900.50),
(9, 'headphones', 24, 3400.50),
(10, 'router', 85, 6700.50),
(11, 'earpods', 4, 300.50),
(12, 'speaker', 39, 1300.0),
(13, 'camera', 1, 1100.45),
(14, 'joystick', 90, 1800.50);

alter sequence product_seq restart with 15;