CREATE TABLE cars (
   id INTEGER PRIMARY KEY,
   doors INTEGER,
   engine_size NUMERIC(3,1),
   color VARCHAR(255),
   fuel_type VARCHAR(255),
   seats INTEGER,
   body_type VARCHAR(255),
   brand VARCHAR(255),
   model VARCHAR(255),
   spec VARCHAR(255),
   prod_year INTEGER,
   description VARCHAR(255),
   registration VARCHAR(10),
   mileage INTEGER,
   value INTEGER
);

CREATE TABLE inventory_items (
   id INTEGER PRIMARY KEY,
   vehicle_id INTEGER REFERENCES cars(id),
   creation_date DATE,
   vehicle_in_stock BOOLEAN
);

CREATE TABLE customers (
   id INTEGER PRIMARY KEY,
   firstname VARCHAR(255),
   surname VARCHAR(255),
   address_line_1 VARCHAR(255),
   address_line_2 VARCHAR(255),
   city VARCHAR(255),
   postcode VARCHAR(255),
   phone VARCHAR(255),
   email VARCHAR(255),
   driver_license_number VARCHAR(255),
   payment_card_name VARCHAR(255),
   payment_card_number VARCHAR(255),
   payment_card_expiry_month INTEGER,
   payment_card_expiry_year INTEGER,
   payment_card_cvv VARCHAR(255),
   dob DATE
);

CREATE TABLE lease_agreements (
   id INTEGER PRIMARY KEY,
   inventory_item_id INTEGER REFERENCES inventory_items(id),
   customer_id INTEGER REFERENCES customers(id),
   policy_start_date DATE,
   policy_expiry_date DATE,
   policy_term INTEGER,
   initial_deposit_months INTEGER,
   total_price NUMERIC(10,2),
   daily_late_fee_percentage INTEGER,
   annual_mileage_allowed INTEGER,
   policy_active BOOLEAN
);

CREATE TABLE users (
   id INTEGER PRIMARY KEY,
   name VARCHAR(255),
   email VARCHAR(255),
   password_hash VARCHAR(255)
);
