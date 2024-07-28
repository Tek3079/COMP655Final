
INSERT INTO public.customer (id, name, email, balance)
VALUES (1, 'Ivy', 'Ivy@example.com', 100);
INSERT INTO public.customer (id, name, email, balance)
VALUES (2, 'Jack', 'Jack@example.com', 10);
INSERT INTO public.customer (id, name, email, balance)
VALUES (3, 'Alice', 'Alice@example.com', 1);
INSERT INTO public.customer (id, name, email, balance)
VALUES (4, 'David', 'David@example.com', 50);
INSERT INTO public.customer (id, name, email, balance)
VALUES (5, 'Hank', 'Hank@example.com', 77);

ALTER SEQUENCE customer_seq RESTART WITH 6;