DROP TABLE IF EXISTS currencies;

CREATE TABLE currencies
(
    id        INTEGER PRIMARY KEY,
    code      varchar NOT NULL,
    full_name varchar NOT NULL,
    sign      varchar NOT NULL
);

INSERT INTO currencies (id, code, full_name, sign)
VALUES (1, 'USD', 'American Dollar', '$'),
       (2, 'RUB', 'Russian Rouble', 'Р');
