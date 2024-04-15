CREATE TABLE currency_rate (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    abbreviation VARCHAR(10) NOT NULL,
    exchange_date DATE NOT NULL,
    code INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    rate NUMERIC NOT NULL
);