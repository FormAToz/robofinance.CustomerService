CREATE TABLE address (
id bigserial NOT NULL,
country varchar(255),
region varchar(255),
city varchar(255),
street varchar(255),
house varchar(255),
flat varchar(255),
created timestamp,
modified timestamp
);

CREATE TABLE customer (
id bigserial NOT NULL,
registered_address_id bigint NOT NULL,
actual_address_id bigint NOT NULL,
first_name varchar(255) NULL,
last_name varchar(255) NULL,
middle_name varchar(255) NULL,
sex varchar(6) NOT NULL
);