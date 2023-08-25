CREATE TABLE IF NOT EXISTS PUBLIC."bank_details"
 (
    id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    version integer NOT NULL,
     created_at timestamp without time zone NOT NULL DEFAULT now(),
     created_by character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
     last_modified_at timestamp without time zone NOT NULL DEFAULT now(),
     last_modified_by character varying(255) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
    account_holder_name   VARCHAR(50),
    bank_name            VARCHAR(50),
    bank_display_name    VARCHAR(50),
    account_number       VARCHAR(20),
    ifsc_code            VARCHAR(15),
    verification_flag    boolean,
    activation_flag      boolean,
    is_rtgs_enabled      boolean,
    is_neft_enabled      boolean,
    payee_id            VARCHAR(50),


    CONSTRAINT fk_payee_details FOREIGN KEY(payee_id) REFERENCES payee_details(id),
    CONSTRAINT bank_details_pkey PRIMARY KEY (id)
 );