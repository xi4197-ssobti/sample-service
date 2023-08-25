----------------------------------------------------------
-- DDL for Table payee_details--
----------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC."payee_details"
 (
    id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    version integer NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT now(),
    created_by character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
    last_modified_at timestamp without time zone NOT NULL DEFAULT now(),
    last_modified_by character varying(255) COLLATE pg_catalog."default" NOT NULL DEFAULT 'admin'::character varying,
            payee_name                  VARCHAR(50),
            customer_id                  VARCHAR(50),
            mobile_number               VARCHAR(15),
            is_active         boolean,
            email_id         VARCHAR(50),
            upi_id           VARCHAR(50),
            pan_number   VARCHAR(20),
            tan_number       VARCHAR(20),
            gst_number       VARCHAR(20),
            billing_name   VARCHAR(50),
            address_line1       VARCHAR(50),
            address_line2       VARCHAR(50),
            pin_code            VARCHAR(10) ,
            city               VARCHAR(50),
            state              VARCHAR(50),
            shipping_name   VARCHAR(50),
            shipping_address_line1       VARCHAR(50),
            shipping_address_line2       VARCHAR(50),
            shipping_pin_code            VARCHAR(10) ,
            shipping_city               VARCHAR(50),
            shipping_state              VARCHAR(50),
            address_flag                boolean DEFAULT false,
    CONSTRAINT payee_details_pkey PRIMARY KEY (id)
 );

 CREATE INDEX customer_id_index ON PUBLIC."payee_details"(customer_id);
