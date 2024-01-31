CREATE TABLE product_sort
(
    id      SERIAL NOT NULL
        CONSTRAINT product_sort_pkey
            PRIMARY KEY,
    name    VARCHAR(256),
    type_id INTEGER
        CONSTRAINT product_sort_type_id_fkey
            REFERENCES product_type
);

CREATE TABLE product_type
(
    id   SERIAL NOT NULL
        CONSTRAINT product_type_pkey
            PRIMARY KEY,
    name VARCHAR(256)
);

CREATE TABLE product
(
    id          SERIAL NOT NULL
        CONSTRAINT product_pkey
            PRIMARY KEY,
    type_id     INTEGER
        CONSTRAINT product_type_id_fkey
            REFERENCES product_type,
    sort_id     INTEGER
        CONSTRAINT product_sort_id_fkey
            REFERENCES product_sort,
    description VARCHAR(256)
);

CREATE TABLE supply
(
    id             SERIAL    NOT NULL
        CONSTRAINT supply_pkey
            PRIMARY KEY,
    supplier_id    INTEGER
        CONSTRAINT supply_supplier_id_fkey
            REFERENCES supplier,
    supply_created TIMESTAMP NOT NULL DEFAULT now(),
    supply_updated TIMESTAMP
);

CREATE TABLE supplier
(
    id      SERIAL NOT NULL
        CONSTRAINT supplier_pkey
            PRIMARY KEY,
    name    VARCHAR(256),
    address VARCHAR(2048),
    phone   DOUBLE PRECISION
        CONSTRAINT supplier_phone UNIQUE,
    CHECK (length(phone::text) BETWEEN 11 AND 15),
    inn     BIGINT UNIQUE
);

CREATE TABLE product_price
(
    id               SERIAL           NOT NULL
        CONSTRAINT product_price_pkey
            PRIMARY KEY,
    product_id       INTEGER
        CONSTRAINT product_price_product_id_fkey
            REFERENCES product        NOT NULL,

    supplier_id      INTEGER
        CONSTRAINT product_price_supplier_id_fkey
            REFERENCES supplier       NOT NULL,
    period_beginning DATE             NOT NULL,
    period_end       DATE             NOT NULL,
    price            DOUBLE PRECISION NOT NULL,
    measure          measure          NOT NULL
);

CREATE TABLE supply_unit
(
    id         SERIAL           NOT NULL
        CONSTRAINT supply_unit_pkey
            PRIMARY KEY,
    supply_id  INTEGER
        CONSTRAINT supply_unit_supply_id_fkey
            REFERENCES supply   NOT NULL,
    product_id INTEGER
        CONSTRAINT supply_unit_product_id_fkey
            REFERENCES product  NOT NULL,
    quantity   INTEGER          NOT NULL,
    price      DOUBLE PRECISION NOT NULL,
    measure    measure          NOT NULL
);

CREATE TYPE measure AS ENUM (
    'GRAM',
    'KG',
    'TONNE');