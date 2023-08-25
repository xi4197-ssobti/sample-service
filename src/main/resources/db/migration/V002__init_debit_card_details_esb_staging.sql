----------------------------------------------------------
-- DDL for Table debit_card_details_esb_staging
----------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC."debit_card_details_esb_staging"
 (
   id character varying(50) COLLATE pg_catalog."default" NOT NULL,
   version integer NOT NULL,
   created_at timestamp without time zone NOT NULL DEFAULT now(),
   created_by character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
   last_modified_at timestamp without time zone NOT NULL DEFAULT now(),
   last_modified_by character varying(255) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
   customer_id           VARCHAR(50) NOT NULL,
   debit_card_details_request VARCHAR(10485760) NOT NULL,
   debit_card_details_response VARCHAR(10485760) NOT NULL
 );

----------------------------------------------------------
-- Constraints for Table USER
----------------------------------------------------------
ALTER TABLE "debit_card_details_esb_staging"
  ADD CONSTRAINT debit_card_details_esb_staging_pk PRIMARY KEY (id);