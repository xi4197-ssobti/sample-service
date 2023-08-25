----------------------------------------------------------
-- DDL for Table customer_360_esb_staging
----------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC."account_360_integration_staging"
 (
   id character varying(50) COLLATE pg_catalog."default" NOT NULL,
   version integer NOT NULL,
   created_at timestamp without time zone NOT NULL DEFAULT now(),
   created_by character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
   last_modified_at timestamp without time zone NOT NULL DEFAULT now(),
   last_modified_by character varying(255) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
   account_number           VARCHAR(50) NOT NULL,
   account_360_request  VARCHAR(10485760) NOT NULL,
   account_360_response VARCHAR(10485760) NOT NULL
 );

----------------------------------------------------------
-- Constraints for Table USER
----------------------------------------------------------
ALTER TABLE "account_360_integration_staging"
  ADD CONSTRAINT account_360_integration_staging_pk PRIMARY KEY (id);