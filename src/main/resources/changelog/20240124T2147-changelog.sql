--liquibase formatted sql

--changeset michel-fernandes:20240124T2147-1
--comment: Creating schema MarketPlace
-- Schema: market_place
DROP SCHEMA IF EXISTS market_place CASCADE;

CREATE SCHEMA market_place;

--rollback DROP SCHEMA cfc CASCADE;

--changeset michel-fernandes:20240124T2147-2
--comment: Creating table vehicle
-- Table: cfc.vehicle_status
CREATE TABLE IF NOT EXISTS market_place.vehicle (
    color varchar(255) NOT NULL,
    created_at timestamp WITHOUT TIME ZONE NOT NULL,
    is_active bool NOT NULL,
    model varchar(255) NOT NULL,
    plate varchar(255) NOT NULL,
    store varchar(255) NOT NULL,
    updated_at timestamp WITHOUT TIME ZONE NULL,
    model_year int4 NOT NULL,
    CONSTRAINT vehicle_pkey PRIMARY KEY (plate)
);

--rollback DROP TABLE market_place.vehicle;