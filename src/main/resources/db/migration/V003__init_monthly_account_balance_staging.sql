----------------------------------------------------------
-- DDL for Table monthly_account_balance_esb_staging
----------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC."monthly_account_balance_esb_staging"
 (
 id character varying(50) COLLATE pg_catalog."default" NOT NULL,
   version integer NOT NULL,
   created_at timestamp without time zone NOT NULL DEFAULT now(),
   created_by character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
   last_modified_at timestamp without time zone NOT NULL DEFAULT now(),
   last_modified_by character varying(255) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
   account_no           VARCHAR(50) NOT NULL,
   monthly_account_balance_request VARCHAR(10485760) NOT NULL,
   monthly_account_balance_response VARCHAR(10485760) NOT NULL

 );

----------------------------------------------------------
-- Constraints for Table USER
----------------------------------------------------------
ALTER TABLE "monthly_account_balance_esb_staging"
  ADD CONSTRAINT monthly_account_balance_esb_staging_pk PRIMARY KEY (id);