CREATE TABLE currencies
(
    id        SERIAL PRIMARY KEY,
    code      VARCHAR NOT NULL,
    full_name VARCHAR NOT NULL,
    sign      VARCHAR NOT NULL
);

CREATE SEQUENCE currencies_sequence START 1;

CREATE UNIQUE INDEX unique_code ON currencies (code);

CREATE TABLE exchange_rates
(
    id INTEGER PRIMARY KEY,
    base_currency_id INTEGER NOT NULL,
    target_currency_id INTEGER NOT NULL,
    rate DECIMAL(6)
);

CREATE SEQUENCE exchange_rates_sequence START 1;

CREATE UNIQUE INDEX unique_exchange_rates ON exchange_rates (base_currency_id, target_currency_id);

INSERT INTO currencies (id, code, full_name, sign)
VALUES (NEXTVAL('currencies_sequence'), 'USD', 'United States dollar', '$'),
       (NEXTVAL('currencies_sequence'), 'EUR', 'Euro', '€'),
       (NEXTVAL('currencies_sequence'), 'RUB', 'Russian rouble', '₽');

INSERT INTO exchange_rates (id, base_currency_id, target_currency_id, rate)
VALUES (NEXTVAL('exchange_rates_sequence'), 2, 1, 101);

