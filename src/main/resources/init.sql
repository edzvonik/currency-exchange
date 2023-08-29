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
    baseCurrencyId INTEGER NOT NULL,
    targetCurrencyId INTEGER NOT NULL,
    rate DECIMAL(6)
);

CREATE SEQUENCE exchange_rates_sequence START 1;

CREATE UNIQUE INDEX unique_exchange_rates ON exchange_rates (baseCurrencyId, targetCurrencyId);

INSERT INTO currencies (id, code, full_name, sign)
VALUES (NEXTVAL('currencies_sequence'), 'USD', 'American Dollar', '$'),
       (NEXTVAL('currencies_sequence'), 'RUB', 'Russian Rouble', 'P'),
       (NEXTVAL('currencies_sequence'), 'EUR', 'Euro', '€');

INSERT INTO exchange_rates (id, baseCurrencyId, targetCurrencyId, rate)
VALUES (NEXTVAL('exchange_rates_sequence'), 2, 1, 101);

