
-- =========================================================
-- Seed Data
-- =========================================================

delete from order_items;
delete from orders;
delete from customers;
delete from products;
delete from categories;
delete from cities;
delete from countries;
delete from regions;
delete from reports;

-- Regions
INSERT INTO regions (name) VALUES
                               ('Americas'),
                               ('EMEA'),
                               ('APAC');

-- Countries
INSERT INTO countries (region_id, name) VALUES
                                            (1, 'United States'),
                                            (1, 'Brazil'),
                                            (2, 'Germany'),
                                            (2, 'France'),
                                            (3, 'Japan'),
                                            (3, 'Australia');

-- Cities
INSERT INTO cities (country_id, name) VALUES
                                          -- USA
                                          (1, 'New York'),
                                          (1, 'San Francisco'),
                                          (1, 'Austin'),
                                          -- Brazil
                                          (2, 'São Paulo'),
                                          (2, 'Rio de Janeiro'),
                                          -- Germany
                                          (3, 'Berlin'),
                                          (3, 'Munich'),
                                          -- Turkey
                                          (4, 'Paris'),
                                          (4, 'Lyon'),
                                          -- Japan
                                          (5, 'Tokyo'),
                                          (5, 'Osaka'),
                                          -- Australia
                                          (6, 'Sydney'),
                                          (6, 'Melbourne');

-- Categories
INSERT INTO categories (name) VALUES
                                  ('Beverages'),
                                  ('Snacks'),
                                  ('Electronics'),
                                  ('Books');

-- Products
INSERT INTO products (category_id, sku, name, unit_price) VALUES
                                                              (1, 'BEV-ESP-001', 'Espresso Beans 1kg', 18.90),
                                                              (1, 'BEV-GRN-002', 'Green Tea 100g',    7.50),
                                                              (2, 'SNK-CHP-010', 'Potato Chips 200g', 3.20),
                                                              (2, 'SNK-CHC-011', 'Dark Chocolate', 4.80),
                                                              (3, 'ELE-HDP-100', 'Headphones Pro',   129.00),
                                                              (3, 'ELE-MSE-101', 'Wireless Mouse',    24.90),
                                                              (4, 'BOOK-SQL-01', 'SQL Essentials',    29.00),
                                                              (4, 'BOOK-REP-02', 'Reporting with Jasper', 39.00);

-- Customers (spread across cities)
INSERT INTO customers (first_name, last_name, email, city_id, created_at) VALUES
                                                                              ('Alice',    'J.', 'alice@example.com',    1,  TIMESTAMP '2025-06-01 10:00:00'),
                                                                              ('Bob',      'S.', 'bob@example.com',      2,  TIMESTAMP '2025-06-03 11:30:00'),
                                                                              ('Carol',    'M.', 'carol@example.com',    3,  TIMESTAMP '2025-06-05 09:15:00'),
                                                                              ('Diego',    'S.', 'diego@example.com',    4,  TIMESTAMP '2025-06-10 14:05:00'),
                                                                              ('Eva',      'S.', 'eva@example.com',      5,  TIMESTAMP '2025-06-12 08:45:00'),
                                                                              ('Franz',    'S.', 'franz@example.com',    6,  TIMESTAMP '2025-06-15 16:20:00'),
                                                                              ('Gisela',   'B.', 'gisela@example.com',   7,  TIMESTAMP '2025-06-18 12:00:00'),
                                                                              ('Henri',    'D.', 'henri@example.com',    8,  TIMESTAMP '2025-06-20 10:10:00'),
                                                                              ('Isabelle', 'M.', 'isabelle@example.com', 9,  TIMESTAMP '2025-06-22 17:30:00'),
                                                                              ('Jun',      'T.', 'jun@example.com',     10,  TIMESTAMP '2025-06-25 13:25:00'),
                                                                              ('Keiko',    'S.', 'keiko@example.com',   11,  TIMESTAMP '2025-06-27 09:50:00'),
                                                                              ('Liam',     'B.', 'liam@example.com',    12,  TIMESTAMP '2025-06-29 15:40:00');

-- Orders
INSERT INTO orders (customer_id, order_date, status) VALUES
                                                         (1,  DATE '2025-07-01', 'PAID'),
                                                         (1,  DATE '2025-07-15', 'SHIPPED'),
                                                         (2,  DATE '2025-07-05', 'PAID'),
                                                         (3,  DATE '2025-07-06', 'PAID'),
                                                         (4,  DATE '2025-07-07', 'CANCELED'),
                                                         (5,  DATE '2025-07-08', 'PAID'),
                                                         (6,  DATE '2025-07-10', 'PAID'),
                                                         (8,  DATE '2025-07-12', 'PAID'),
                                                         (9,  DATE '2025-07-13', 'PAID'),
                                                         (10, DATE '2025-07-14', 'PAID'),
                                                         (11, DATE '2025-07-15', 'NEW'),
                                                         (12, DATE '2025-07-16', 'PAID'),
                                                         (5,  DATE '2025-07-18', 'PAID'),
                                                         (5,  DATE '2025-07-25', 'SHIPPED'),
                                                         (6,  DATE '2025-07-20', 'PAID'),
                                                         (6,  DATE '2025-07-28', 'PAID'),
                                                         (8,  DATE '2025-07-22', 'PAID'),
                                                         (8,  DATE '2025-07-30', 'PAID'),
                                                         (9,  DATE '2025-07-19', 'PAID'),
                                                         (9,  DATE '2025-07-26', 'SHIPPED'),
                                                         (10, DATE '2025-07-21', 'PAID'),
                                                         (10, DATE '2025-07-29', 'PAID'),
                                                         (11, DATE '2025-07-23', 'PAID'),
                                                         (11, DATE '2025-07-31', 'PAID');

-- Order Items
-- (order_id, product_id, quantity, unit_price at the time)
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
                                                                         -- Existing order items (orders 1-12)
                                                                         (1, 1,  2, 18.90),   -- Alice: Espresso Beans
                                                                         (1, 6,  1, 24.90),   -- Alice: Mouse
                                                                         (2, 5,  1, 129.00),  -- Alice: Headphones
                                                                         (2, 4,  3, 4.80),    -- Alice: Chocolate
                                                                         (3, 2,  4, 7.50),    -- Bob: Green Tea
                                                                         (3, 7,  1, 29.00),   -- Bob: SQL Book
                                                                         (4, 3,  5, 3.20),    -- Carol: Chips
                                                                         (4, 8,  1, 39.00),   -- Carol: Jasper Book
                                                                         (5, 1,  1, 18.90),   -- Diego: Espresso (CANCELED order)
                                                                         (6, 5,  2, 129.00),  -- Eva: Headphones
                                                                         (7, 6,  3, 24.90),   -- Franz: Mouse
                                                                         (8, 2,  2, 7.50),    -- Henri: Green Tea
                                                                         (9, 4,  5, 4.80),    -- Isabelle: Chocolate
                                                                         (10, 8, 2, 39.00),   -- Jun: Jasper Book
                                                                         (11, 3,  1, 3.20),   -- Keiko: Chips (NEW order)
                                                                         (12, 1,  1, 18.90),  -- Liam: Espresso
                                                                         (12, 5,  1, 129.00), -- Liam: Headphones
                                                                         -- Additional order items for Eva (customer 5, orders 13-14)
                                                                         (13, 3,  3, 3.20),   -- Eva: Chips (Snacks)
                                                                         (13, 7,  1, 29.00),  -- Eva: SQL Book (Books)
                                                                         (14, 2,  2, 7.50),   -- Eva: Green Tea (Beverages)
                                                                         (14, 6,  1, 24.90),  -- Eva: Mouse (Electronics)
                                                                         -- Additional order items for Franz (customer 6, orders 15-16)
                                                                         (15, 1,  2, 18.90),  -- Franz: Espresso (Beverages)
                                                                         (15, 4,  4, 4.80),   -- Franz: Chocolate (Snacks)
                                                                         (16, 8,  1, 39.00),  -- Franz: Jasper Book (Books)
                                                                         (16, 2,  3, 7.50),   -- Franz: Green Tea (Beverages)
                                                                         -- Additional order items for Henri (customer 8, orders 17-18)
                                                                         (17, 5,  1, 129.00), -- Henri: Headphones (Electronics)
                                                                         (17, 7,  1, 29.00),  -- Henri: SQL Book (Books)
                                                                         (18, 4,  2, 4.80),   -- Henri: Chocolate (Snacks)
                                                                         (18, 1,  1, 18.90),  -- Henri: Espresso (Beverages)
                                                                         -- Additional order items for Isabelle (customer 9, orders 19-20)
                                                                         (19, 6,  2, 24.90),  -- Isabelle: Mouse (Electronics)
                                                                         (19, 8,  1, 39.00),  -- Isabelle: Jasper Book (Books)
                                                                         (20, 3,  4, 3.20),   -- Isabelle: Chips (Snacks)
                                                                         (20, 1,  2, 18.90),  -- Isabelle: Espresso (Beverages)
                                                                         -- Additional order items for Jun (customer 10, orders 21-22)
                                                                         (21, 2,  3, 7.50),   -- Jun: Green Tea (Beverages)
                                                                         (21, 6,  1, 24.90),  -- Jun: Mouse (Electronics)
                                                                         (22, 7,  2, 29.00),  -- Jun: SQL Book (Books)
                                                                         (22, 3,  2, 3.20),   -- Jun: Chips (Snacks)
                                                                         -- Additional order items for Keiko (customer 11, orders 23-24)
                                                                         (23, 4,  3, 4.80),   -- Keiko: Chocolate (Snacks)
                                                                         (23, 5,  1, 129.00), -- Keiko: Headphones (Electronics)
                                                                         (24, 1,  2, 18.90),  -- Keiko: Espresso (Beverages)
                                                                         (24, 2,  2, 7.50);   -- Keiko: Green Tea (Beverages)

INSERT INTO reports (name, compiled_report_file)
VALUES ('Customers', 'Customers.jasper');

INSERT INTO reports (name, compiled_report_file)
VALUES ('Countries', 'region/Countries.jasper');

INSERT INTO reports (name, compiled_report_file)
VALUES ('CustomerSpend', 'CustomerSpend.jasper');

INSERT INTO reports (name, compiled_report_file)
VALUES ('Orders', 'Orders.jasper');
