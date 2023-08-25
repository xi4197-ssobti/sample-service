----------------------------------------------------------
-- DDL for Table payee_details
----------------------------------------------------------

CREATE TABLE IF NOT EXISTS PUBLIC."account_verification_staging"
 (
    id character varying(50) COLLATE pg_catalog."default" NOT NULL,
        version integer NOT NULL,
        created_at timestamp without time zone NOT NULL DEFAULT now(),
        created_by character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
        last_modified_at timestamp without time zone NOT NULL DEFAULT now(),
        last_modified_by character varying(255) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
        account_number           VARCHAR(30) NOT NULL,
        account_verification_request VARCHAR(10485760) NOT NULL,
        account_verification_response VARCHAR(10485760) NOT NULL,
    CONSTRAINT account_verification_staging_pkey PRIMARY KEY (id)
 );