CREATE TABLE warehouses(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE shops(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE products(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(10,2),
    quantity INT,
    warehouse_id BIGINT,
    shop_id BIGINT,

    CONSTRAINT fk_product_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(id),
    CONSTRAINT fk_product_shop FOREIGN KEY (shop_id) REFERENCES shops(id)
);

CREATE TABLE shop_warehouse(
    shop_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,

    PRIMARY KEY (shop_id, warehouse_id),

    CONSTRAINT fk_shop FOREIGN KEY (shop_id) REFERENCES shops(id),
    CONSTRAINT fk_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(id)
);