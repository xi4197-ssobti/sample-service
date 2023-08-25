----------------------------------------------------------
-- DDL for Table mini_statement_esb_staging
----------------------------------------------------------

CREATE TABLE IF NOT EXISTS PUBLIC."mini_statement_esb_staging"
 (
    id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    version integer NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT now(),
    created_by character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
    last_modified_at timestamp without time zone NOT NULL DEFAULT now(),
    last_modified_by character varying(255) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
    account_id           VARCHAR(50) NOT NULL,
    mini_statement_request VARCHAR(10485760) NOT NULL,
    mini_statement_response VARCHAR(10485760) NOT NULL,
    CONSTRAINT mini_statement_esb_staging_pkey PRIMARY KEY (id)
 );