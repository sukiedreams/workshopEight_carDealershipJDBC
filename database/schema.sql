# ---------------------------------------------------------------------- #
# Target DBMS:           MySQL                                           #
# Project name:          CarDealershipDatabase                           #
# ---------------------------------------------------------------------- #

DROP DATABASE IF EXISTS cardealershipdatabase;

CREATE DATABASE IF NOT EXISTS cardealershipdatabase;

USE cardealershipdatabase;

# ---------------------------------------------------------------------- #
# Tables                                                                 #
# ---------------------------------------------------------------------- #
# ---------------------------------------------------------------------- #
# Add table "dealerships"                                                 #
# ---------------------------------------------------------------------- #

CREATE TABLE dealerships (
	dealership_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    address VARCHAR(50),
    phone VARCHAR(12));

# ---------------------------------------------------------------------- #
# Add table "vehicles"                                                   #
# ---------------------------------------------------------------------- #

    CREATE TABLE vehicles (
		VIN VARCHAR(17) PRIMARY KEY,
        make VARCHAR(30),
        model VARCHAR(30),
        color VARCHAR(20),
        year INT,
        price DECIMAL(10,2),
        SOLD BOOLEAN);

# ---------------------------------------------------------------------- #
# Add table "inventory"                                                  #
# ---------------------------------------------------------------------- #

	CREATE TABLE inventory (
        dealership_id INT,
        VIN VARCHAR(17),
        FOREIGN KEY ( dealership_id) REFERENCES dealerships(dealership_id),
        FOREIGN KEY (VIN) REFERENCES vehicles(VIN));

# ---------------------------------------------------------------------- #
# Add table "salescontracts"                                             #
# ---------------------------------------------------------------------- #

	CREATE TABLE sales_contracts (
		contract_id INT AUTO_INCREMENT PRIMARY KEY,
        VIN VARCHAR(17),
        sale_date DATE,
        buyer_name VARCHAR(50),
        sale_price DECIMAL(10,2),
        FOREIGN KEY (VIN) REFERENCES vehicles(VIN));

# ---------------------------------------------------------------------- #
# Add table "leasecontracts"                                             #
# ---------------------------------------------------------------------- #

	CREATE TABLE lease_contracts (
		lease_id INT AUTO_INCREMENT PRIMARY KEY,
        VIN VARCHAR(17),
        lease_start DATE,
        lease_end DATE,
        monthly_payment DECIMAL(10,2),
        FOREIGN KEY (VIN) REFERENCES vehicles(VIN));


INSERT INTO dealerships (name, address, phone) VALUES
('Sukies Auto', '123 main st', '555-123-4567'),
('Kens Motors', '456 Center Blvd', '555-987-6543');

INSERT INTO vehicles (VIN, make, model, color, year, price, SOLD) VALUES
('10112', 'Ford', 'Explorer', 'red', 1993, 995.00, FALSE),
('56233', 'Honda', 'Civic', 'Blue', 2015, 8500.00, TRUE),
('12874','Mazda', 'CX-5', 'White', 2011, 8495.00, FALSE),
('13985', 'BMW', '328i', 'Black', 2008, 7495.00, TRUE),
('14562', 'Volkswagen', 'Jetta', 'Silver', 2014, 8995.00, FALSE),
('15234', 'Chevrolet', 'Equinox', 'Gray', 2016, 10995.00, FALSE),
('17548', 'Dodge', 'Charger', 'Blue', 2015,11995.00, FALSE),
('18691', 'Toyota', 'Prius', 'Green', 2013, 9995.00, TRUE),
('19384', 'GMC', 'Terrain', 'Maroon', 2017, 12495.00, TRUE),
('20476', 'Hyundai', 'Elantra', 'White', 2020, 13495.00, FALSE),
('21937', 'Nissan', 'Rogue', 'Black', 2018, 13995.00, FALSE);

INSERT INTO inventory (dealership_id, VIN) VALUES
(1, '10112'),
(2, '56233'),
(1, '12874'),
(2, '13985'),
(1, '14562'),
(2, '15234'),
(1, '17548'),
(2, '18691'),
(1, '19384'),
(2, '20476'),
(1, '21937');

INSERT INTO sales_contracts (VIN, sale_date, buyer_name, sale_price) VALUES
('13985', '2025-06-07', 'Alice Jade', 7495.00),
('56233', '2020-12-25', 'Sukie Dreams', 8500.00),
('18691', '2021-05-09', 'Kendall Wildy', 9995.00),
('19384', '2018-01-19', 'Siomara Ayala', 12495.00),
('56233', '2025-09-11', 'Harley Vargas', 8500.00);

INSERT INTO lease_contracts (VIN, lease_start, lease_end, monthly_payment) VALUES
('12874', '2025-06-07', '2027-05-07', 312.29),
('18691', '2021-05-09', '2023-04-09', 416.45),
('56233', '2025-09-11', '2027-08-11', 354.16);