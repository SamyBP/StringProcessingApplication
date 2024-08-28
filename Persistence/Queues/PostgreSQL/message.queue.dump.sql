--
-- PostgreSQL database dump
--

-- Dumped from database version 15.7
-- Dumped by pg_dump version 15.7

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: mq; Type: SCHEMA; Schema: -; Owner: mq_admin
--

CREATE SCHEMA mq;


ALTER SCHEMA mq OWNER TO mq_admin;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: queue; Type: TABLE; Schema: mq; Owner: mq_admin
--

CREATE TABLE mq.queue (
    id integer NOT NULL,
    message text NOT NULL,
    result text,
    is_available boolean DEFAULT true,
    is_success boolean,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE mq.queue OWNER TO mq_admin;

--
-- Name: queue_id_seq; Type: SEQUENCE; Schema: mq; Owner: mq_admin
--

CREATE SEQUENCE mq.queue_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mq.queue_id_seq OWNER TO mq_admin;

--
-- Name: queue_id_seq; Type: SEQUENCE OWNED BY; Schema: mq; Owner: mq_admin
--

ALTER SEQUENCE mq.queue_id_seq OWNED BY mq.queue.id;


--
-- Name: queue id; Type: DEFAULT; Schema: mq; Owner: mq_admin
--

ALTER TABLE ONLY mq.queue ALTER COLUMN id SET DEFAULT nextval('mq.queue_id_seq'::regclass);


--
-- Name: queue queue_pkey; Type: CONSTRAINT; Schema: mq; Owner: mq_admin
--

ALTER TABLE ONLY mq.queue
    ADD CONSTRAINT queue_pkey PRIMARY KEY (id);


--
-- Name: isavailable_idx; Type: INDEX; Schema: mq; Owner: mq_admin
--

CREATE INDEX isavailable_idx ON mq.queue USING btree (is_available) WHERE (is_available = true);


--
-- PostgreSQL database dump complete
--

