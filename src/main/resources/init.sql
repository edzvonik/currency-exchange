DROP TABLE IF EXISTS currencies;

CREATE TABLE currencies
(
    id        INTEGER PRIMARY KEY,
    code      varchar NOT NULL,
    full_name varchar NOT NULL,
    sign      varchar NOT NULL
);

CREATE TABLE exchange_rates
(
    id INTEGER PRIMARY KEY,
    baseCurrencyId INTEGER NOT NULL,
    targetCurrencyId INTEGER NOT NULL,
    rate DECIMAL(6)
);

INSERT INTO currencies (id, code, full_name, sign)
VALUES (1, 'USD', 'Американский доллар', '$'),
       (2, 'RUB', 'Российский рубль', '₽');

INSERT INTO exchange_rates (id, baseCurrencyId, targetCurrencyId, rate)
VALUES (1, 2, 1, 101);

