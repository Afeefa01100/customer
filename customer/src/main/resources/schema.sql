DROP TABLE IF EXISTS Customer;
create table Customer(
		 customer_Id UUID PRIMARY KEY,
		customer_first_name VARCHAR(255),
		customer_middle_name VARCHAR(255),
		customer_last_name VARCHAR(255),
		customer_email VARCHAR(255) UNIQUE,
		customer_phone_number VARCHAR(255)
		);
