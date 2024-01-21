CREATE SCHEMA IF NOT EXISTS market;


-- public.vehicle definition
-- Drop table

-- DROP TABLE vehicle;

CREATE TABLE  IF NOT EXISTS market.vehicle (
     color varchar(255) NOT NULL,
     createdAt timestamp WITHOUT TIME ZONE NOT NULL,
     isActive bool NOT NULL,
     model varchar(255) NOT NULL,
     plate varchar(255) NOT NULL,
     store varchar(255) NOT NULL,
     updatedAt timestamp WITHOUT TIME ZONE NULL,
     "year" int4 NOT NULL,
     CONSTRAINT vehicle_pkey PRIMARY KEY (plate)
);