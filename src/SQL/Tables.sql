CREATE TABLE LORO_Shopping_Cart
(
  cart_id INT unsigned NOT NULL AUTO_INCREMENT, # Unique ID for the record
  customer_id INT NOT NULL,
  order_id INT NOT NULL,
  date_added DATE NOT NULL,  
  payment_status VARCHAR(20) NOT NULL,
  payment_type VARCHAR(20) NOT NULL,
  discount_type VARCHAR(30),
  discount_number int,
  PRIMARY KEY  (cart_id)                                  
);

CREATE TABLE LORO_Ordered_Products
(
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL
);

CREATE TABLE LORO_Address(
address_id INT NOT NULL,
street VARCHAR(50) NOT NULL,
number INT NOT NULL,
building VARCHAR(20),
apartament VARCHAR(10),
floor VARCHAR(10),
city VARCHAR(50) NOT NULL,
county VARCHAR(50) NOT NULL,
country VARCHAR(50) NOT NULL,
zip_code INT NOT NULL,
  PRIMARY KEY (address_id)
);

CREATE TABLE LORO_Products
(
  product_id INT unsigned NOT NULL AUTO_INCREMENT, # Unique ID for the record
  name VARCHAR(50) NOT NULL,
  brand VARCHAR(50) NOT NULL,
  price DOUBLE NOT NULL,
  color VARCHAR(20),
  size VARCHAR(50),
  description VARCHAR(1000) NOT NULL,
  category_id INT NOT NULL,
  subcategory_id INT NOT NULL,
  PRIMARY KEY  (product_id)        
);

CREATE TABLE LORO_Customers
(
  customer_id INT unsigned NOT NULL AUTO_INCREMENT, # Unique ID for the record
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  phone INT NOT NULL,
  CNP CHAR(13),
  address_id int NOT NULL,
  status VARCHAR(20) NOT NULL,
  PRIMARY KEY (customer_id)
);


CREATE TABLE LORO_Categories
(
  category_id INT unsigned NOT NULL AUTO_INCREMENT, # Unique ID for the record
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (category_id)
);

CREATE TABLE LORO_Subcategories
(
  subcategory_id INT unsigned NOT NULL AUTO_INCREMENT, # Unique ID for the record
  name VARCHAR(100) NOT NULL,
  category_id INT NOT NULL,
  PRIMARY KEY (subcategory_id)
);


CREATE TABLE LORO_Stock
(
  stock_id INT unsigned NOT NULL AUTO_INCREMENT, # Unique ID for the record
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  status VARCHAR(20) NOT NULL,
  PRIMARY KEY (stock_id)
);

DROP TABLE LORO_Provider;

CREATE TABLE LORO_Provider
(
  provider_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  fiscal_code VARCHAR(20) NOT NULL,
  trade_register VARCHAR(50) NOT NULL,
  center VARCHAR(100) NOT NULL,
  capital INT NOT NULL,
  phone VARCHAR(50) NOT NULL,
  bank VARCHAR(50) NOT NULL,
  account CHAR(24) NOT NULL,
  vat_rate INT NOT NULL,
  PRIMARY KEY (provider_id)
);

DROP TABLE LORO_Receipts;

CREATE TABLE LORO_Receipts
(
	order_id INT NOT NULL,
    receipt BLOB NOT NULL,
    amount INT NOT NULL,
    payment_type VARCHAR(20) NOT NULL,
    subcategory_id INT NOT NULL,
	PRIMARY KEY (order_id)
);

INSERT INTO LORO_Provider (provider_id, name, fiscal_code, trade_register, center, capital, phone, bank, account, vat_rate)
VALUES (1,'S.C. LORO S.R.L.','RO27456385','J22/222/2018','Str. Victor Mihailescu Craiu, Nr. 4D, Iasi, Romania', 
'200', '0232263455 / 0752365482', 'Transilvania SA', 'RO67BTRLRONCRT020325232','19');

SELECT * FROM LORO_Shopping_Cart;
SELECT * FROM LORO_Ordered_Products;
SELECT * FROM LORO_Products;
SELECT * FROM LORO_Customers;
SELECT * FROM LORO_Categories;
SELECT * FROM LORO_Subcategories;
SELECT * FROM LORO_Stock;
SELECT * FROM LORO_Provider;
SELECT * FROM LORO_Address;
SELECT * FROM LORO_Receipts;

SELECT A.order_id, A.quantity, B.product_id, B.name, B.price, B.brand, B.description 
FROM LORO_Ordered_Products A
JOIN LORO_Products B ON A.product_id = B.product_id
WHERE A.order_id = 1200;


SELECT name, fiscal_code, trade_register, center, capital, phone, bank, account, vat_rate 
FROM LORO_Provider
WHERE provider_id = 1;

SELECT A.order_id, B.customer_id, B.first_name, B.last_name, B.phone, B.CNP, B.address_id, B.status  
FROM LORO_Shopping_Carts A
JOIN LORO_Costumers B ON A.customer_id = B.customer_id
WHERE order_id = 1200;

SELECT A.order_id, A.quantity, B.product_id, B.name, B.price, B.brand, B.description 
FROM LORO_Ordered_Products A
JOIN LORO_Products B ON A.product_id = B.product_id
WHERE A.order_id = 1200;

SELECT * FROM LORO_Shopping_Cart
WHERE month(date_added) = 12;

SELECT A.order_id, B.product_id, B.subcategory_id, B.name, B.price FROM LORO_Ordered_Products A
JOIN LORO_Products B ON A.product_id = B.product_id
WHERE order_id IN(1203,1204,1210,1213) AND B.product_id = 5001;

SELECT A.order_id, A.quantity, B.product_id, B.name, 
B.price, B.brand, B.description, B.subcategory_id, C.date_added
FROM LORO_Ordered_Products A
JOIN LORO_Products B ON A.product_id = B.product_id
JOIN LORO_Shopping_Cart C ON A.order_id = C.order_id;

SELECT A.product_id, B.product_id, B.price, B.category_id, B.subcategory_id, 
C.category_id, C.name
FROM LORO_Ordered_Products A
JOIN LORO_Products B ON A.product_id = B.product_id 
JOIN LORO_Categories C ON B.category_id = C.category_id;



SELECT * FROM LORO_Carts;