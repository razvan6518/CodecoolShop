CREATE TABLE IF NOT EXISTS products (
                                        id serial PRIMARY KEY,
                                        default_price bigint,
                                        default_currency JSON,
                                        product_category JSON,
                                        supplier JSON
);

CREATE TABLE IF NOT EXISTS product_categories (
                                        id serial PRIMARY KEY,
                                        department varchar,
                                        products JSON
);

CREATE TABLE IF NOT EXISTS suppliers (
                                         id serial PRIMARY KEY,
                                         department varchar,
                                         products JSON
);

CREATE TABLE IF NOT EXISTS users (
                                         id serial PRIMARY KEY,
                                         name varchar,
                                         email varchar,
                                         password varchar
);