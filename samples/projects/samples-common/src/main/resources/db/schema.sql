-- =========================================================
-- DEMO DB (H2)
-- Features:
--  - Regions → Countries → Cities (cascading controls)
--  - Categories → Products (cascading controls)
--  - Customers, Orders, Order Items (master-detail & subreport)
--  - A reporting view (order summaries)
-- =========================================================

-- ---------- Clean up (idempotent reruns) ----------
DROP VIEW IF EXISTS v_order_summary;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS regions;
DROP TABLE IF EXISTS reports;

-- ---------- Lookups for cascading controls ----------
CREATE TABLE regions (
                         id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         name        VARCHAR(100) NOT NULL
);

CREATE TABLE countries (
                           id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           region_id   INTEGER NOT NULL,
                           name        VARCHAR(100) NOT NULL,
                           CONSTRAINT fk_country_region FOREIGN KEY (region_id) REFERENCES regions(id)
);

CREATE TABLE cities (
                        id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        country_id  INTEGER NOT NULL,
                        name        VARCHAR(100) NOT NULL,
                        CONSTRAINT fk_city_country FOREIGN KEY (country_id) REFERENCES countries(id)
);

-- ---------- Product catalog (second cascade) ----------
CREATE TABLE categories (
                            id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            name        VARCHAR(100) NOT NULL
);

CREATE TABLE products (
                          id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          category_id   INTEGER NOT NULL,
                          sku           VARCHAR(40)  NOT NULL UNIQUE,
                          name          VARCHAR(200) NOT NULL,
                          unit_price    DECIMAL(12,2) NOT NULL,
                          CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- ---------- Customers & Sales ----------
CREATE TABLE customers (
                           id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           first_name  VARCHAR(100) NOT NULL,
                           last_name   VARCHAR(100) NOT NULL,
                           email       VARCHAR(200) NOT NULL,
                           city_id     INTEGER NOT NULL,
                           created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           CONSTRAINT fk_customer_city FOREIGN KEY (city_id) REFERENCES cities(id)
);

CREATE TABLE orders (
                        id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        customer_id   INTEGER NOT NULL,
                        order_date    DATE NOT NULL,
                        status        VARCHAR(20) NOT NULL,       -- e.g., NEW, PAID, SHIPPED, CANCELED
                        CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE order_items (
                             id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             order_id      INTEGER NOT NULL,
                             product_id    INTEGER NOT NULL,
                             quantity      INTEGER NOT NULL CHECK (quantity > 0),
                             unit_price    DECIMAL(12,2) NOT NULL,
                             CONSTRAINT fk_item_order   FOREIGN KEY (order_id)  REFERENCES orders(id),
                             CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE reports (
                         id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         name        VARCHAR(100) NOT NULL,
                         compiled_report_file VARCHAR(255) NOT NULL
);

-- ---------- Helpful indexes ----------
CREATE UNIQUE INDEX ux_reports_name ON reports(name);
CREATE INDEX ix_countries_region ON countries(region_id);
CREATE INDEX ix_cities_country   ON cities(country_id);
CREATE INDEX ix_products_cat     ON products(category_id);
CREATE INDEX ix_customers_city   ON customers(city_id);
CREATE INDEX ix_orders_customer  ON orders(customer_id);
CREATE INDEX ix_items_order      ON order_items(order_id);

-- =========================================================
-- Reporting View (handy for summary reports)
-- =========================================================
CREATE VIEW v_order_summary AS
SELECT
    o.id                 AS order_id,
    o.order_date,
    o.status,
    c.id                 AS customer_id,
    c.first_name || ' ' || c.last_name AS customer_name,
    r.name               AS region_name,
    co.name              AS country_name,
    ci.name              AS city_name,
    SUM(oi.quantity * oi.unit_price)   AS order_total
FROM orders o
         JOIN customers c ON c.id = o.customer_id
         JOIN cities ci   ON ci.id = c.city_id
         JOIN countries co ON co.id = ci.country_id
         JOIN regions r    ON r.id  = co.region_id
         JOIN order_items oi ON oi.order_id = o.id
GROUP BY o.id, o.order_date, o.status, c.id, c.first_name, c.last_name, r.name, co.name, ci.name;

-- =========================================================
-- Example control queries (for Jasper input controls)
-- (Use these as the SQL for your controls; parameter names in braces)
-- =========================================================

-- 1) Region list (top-level)
-- SELECT id, name FROM regions ORDER BY name;

-- 2) Countries filtered by selected region
-- Input: $P{RegionId}
-- SELECT id, name FROM countries WHERE region_id = $P{RegionId} ORDER BY name;

-- 3) Cities filtered by selected country
-- Input: $P{CountryId}
-- SELECT id, name FROM cities WHERE country_id = $P{CountryId} ORDER BY name;

-- 4) Categories (top-level)
-- SELECT id, name FROM categories ORDER BY name;

-- 5) Products filtered by selected category (supports multi-select with $X{IN,...})
-- Input: $P{CategoryId}
-- SELECT id, name FROM products WHERE category_id = $P{CategoryId} ORDER BY name;

-- =========================================================
-- Example main report dataset (with optional filters)
-- (Use $X{IN, p.category_id, CategoryIds} for multi-select)
-- =========================================================
-- Parameters you can define:
--   RegionId (Integer, optional)
--   CountryId (Integer, optional)
--   CityId (Integer, optional)
--   CategoryIds (Collection<Integer>, optional)
--   DateFrom (Date, optional)
--   DateTo   (Date, optional)

-- SELECT
--   os.*
-- FROM v_order_summary os
-- JOIN orders o ON o.id = os.order_id
-- JOIN order_items oi ON oi.order_id = o.id
-- JOIN products p ON p.id = oi.product_id
-- WHERE (CAST(? AS INTEGER) IS NULL OR os.region_name IN (
--          SELECT r.name FROM regions r WHERE r.id = CAST(? AS INTEGER)
--       ))
--   AND (CAST(? AS INTEGER) IS NULL OR os.country_name IN (
--          SELECT co.name FROM countries co WHERE co.id = CAST(? AS INTEGER)
--       ))
--   AND (CAST(? AS INTEGER) IS NULL OR os.city_name IN (
--          SELECT ci.name FROM cities ci WHERE ci.id = CAST(? AS INTEGER)
--       ))
--   AND ($X{IN, p.category_id, CategoryIds})
--   AND (CAST(? AS DATE) IS NULL OR o.order_date >= CAST(? AS DATE))
--   AND (CAST(? AS DATE) IS NULL OR o.order_date <  CAST(? AS DATE) + 1)
-- ORDER BY os.order_date DESC, os.order_id;

-- For the IN-clause above, configure CategoryIds as a multi-select collection parameter in Jasper.
