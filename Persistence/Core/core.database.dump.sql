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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: execution; Type: TABLE; Schema: public; Owner: stc_admin
--

CREATE TABLE public.execution (
    id integer NOT NULL,
    user_id integer,
    pipe_id integer,
    status text DEFAULT 'NOT_PROCESSED'::text NOT NULL,
    result text DEFAULT 'UNDEFINED'::text,
    input text NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    started_at timestamp without time zone,
    ended_at timestamp without time zone,
    version smallint,
    CONSTRAINT execution_log_status_check CHECK ((status = ANY (ARRAY['NOT_PROCESSED'::text, 'ASSIGNED'::text, 'FINISHED'::text, 'ERROR'::text]))),
    CONSTRAINT execution_log_version_check CHECK ((version = ANY (ARRAY[1, 2])))
);


ALTER TABLE public.execution OWNER TO stc_admin;

--
-- Name: execution_id_seq; Type: SEQUENCE; Schema: public; Owner: stc_admin
--

CREATE SEQUENCE public.execution_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.execution_id_seq OWNER TO stc_admin;

--
-- Name: execution_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: stc_admin
--

ALTER SEQUENCE public.execution_id_seq OWNED BY public.execution.id;


--
-- Name: execution_module; Type: TABLE; Schema: public; Owner: stc_admin
--

CREATE TABLE public.execution_module (
    id integer NOT NULL,
    execution_id integer,
    args text NOT NULL,
    module_name text,
    CONSTRAINT execution_module_module_name_check CHECK ((module_name = ANY (ARRAY['SUBSTRING'::text, 'TRIM'::text, 'LOWER'::text, 'UPPER'::text])))
);


ALTER TABLE public.execution_module OWNER TO stc_admin;

--
-- Name: execution_module_id_seq; Type: SEQUENCE; Schema: public; Owner: stc_admin
--

CREATE SEQUENCE public.execution_module_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.execution_module_id_seq OWNER TO stc_admin;

--
-- Name: execution_module_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: stc_admin
--

ALTER SEQUENCE public.execution_module_id_seq OWNED BY public.execution_module.id;


--
-- Name: module; Type: TABLE; Schema: public; Owner: stc_admin
--

CREATE TABLE public.module (
    id integer NOT NULL,
    name text NOT NULL,
    args json NOT NULL
);


ALTER TABLE public.module OWNER TO stc_admin;

--
-- Name: module_id_seq; Type: SEQUENCE; Schema: public; Owner: stc_admin
--

CREATE SEQUENCE public.module_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.module_id_seq OWNER TO stc_admin;

--
-- Name: module_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: stc_admin
--

ALTER SEQUENCE public.module_id_seq OWNED BY public.module.id;


--
-- Name: pipe; Type: TABLE; Schema: public; Owner: stc_admin
--

CREATE TABLE public.pipe (
    id integer NOT NULL,
    user_id integer,
    name text NOT NULL,
    is_public boolean DEFAULT false NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.pipe OWNER TO stc_admin;

--
-- Name: pipe_id_seq; Type: SEQUENCE; Schema: public; Owner: stc_admin
--

CREATE SEQUENCE public.pipe_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pipe_id_seq OWNER TO stc_admin;

--
-- Name: pipe_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: stc_admin
--

ALTER SEQUENCE public.pipe_id_seq OWNED BY public.pipe.id;


--
-- Name: pipe_module; Type: TABLE; Schema: public; Owner: stc_admin
--

CREATE TABLE public.pipe_module (
    id integer NOT NULL,
    pipe_id integer,
    module_id integer
);


ALTER TABLE public.pipe_module OWNER TO stc_admin;

--
-- Name: pipe_module_id_seq; Type: SEQUENCE; Schema: public; Owner: stc_admin
--

CREATE SEQUENCE public.pipe_module_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pipe_module_id_seq OWNER TO stc_admin;

--
-- Name: pipe_module_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: stc_admin
--

ALTER SEQUENCE public.pipe_module_id_seq OWNED BY public.pipe_module.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: stc_admin
--

CREATE TABLE public.users (
    id integer NOT NULL,
    email text NOT NULL,
    password text NOT NULL,
    is_active boolean DEFAULT true NOT NULL,
    last_login timestamp without time zone,
    username text NOT NULL
);


ALTER TABLE public.users OWNER TO stc_admin;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: stc_admin
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO stc_admin;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: stc_admin
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: execution id; Type: DEFAULT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.execution ALTER COLUMN id SET DEFAULT nextval('public.execution_id_seq'::regclass);


--
-- Name: execution_module id; Type: DEFAULT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.execution_module ALTER COLUMN id SET DEFAULT nextval('public.execution_module_id_seq'::regclass);


--
-- Name: module id; Type: DEFAULT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.module ALTER COLUMN id SET DEFAULT nextval('public.module_id_seq'::regclass);


--
-- Name: pipe id; Type: DEFAULT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.pipe ALTER COLUMN id SET DEFAULT nextval('public.pipe_id_seq'::regclass);


--
-- Name: pipe_module id; Type: DEFAULT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.pipe_module ALTER COLUMN id SET DEFAULT nextval('public.pipe_module_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: execution; Type: TABLE DATA; Schema: public; Owner: stc_admin
--

COPY public.execution (id, user_id, pipe_id, status, result, input, created_at, started_at, ended_at, version) FROM stdin;
\.


--
-- Data for Name: execution_module; Type: TABLE DATA; Schema: public; Owner: stc_admin
--

COPY public.execution_module (id, execution_id, args, module_name) FROM stdin;
\.


--
-- Data for Name: module; Type: TABLE DATA; Schema: public; Owner: stc_admin
--

COPY public.module (id, name, args) FROM stdin;
1	SUBSTRING	{"start": 0, "end": 0}
2	TRIM	{"character": ""}
3	UPPER	{}
4	LOWER	{}
\.


--
-- Data for Name: pipe; Type: TABLE DATA; Schema: public; Owner: stc_admin
--

COPY public.pipe (id, user_id, name, is_public, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: pipe_module; Type: TABLE DATA; Schema: public; Owner: stc_admin
--

COPY public.pipe_module (id, pipe_id, module_id) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: stc_admin
--

COPY public.users (id, email, password, is_active, last_login, username) FROM stdin;
\.


--
-- Name: execution_id_seq; Type: SEQUENCE SET; Schema: public; Owner: stc_admin
--

SELECT pg_catalog.setval('public.execution_id_seq', 1, false);


--
-- Name: execution_module_id_seq; Type: SEQUENCE SET; Schema: public; Owner: stc_admin
--

SELECT pg_catalog.setval('public.execution_module_id_seq', 1, false);


--
-- Name: module_id_seq; Type: SEQUENCE SET; Schema: public; Owner: stc_admin
--

SELECT pg_catalog.setval('public.module_id_seq', 4, true);


--
-- Name: pipe_id_seq; Type: SEQUENCE SET; Schema: public; Owner: stc_admin
--

SELECT pg_catalog.setval('public.pipe_id_seq', 1, false);


--
-- Name: pipe_module_id_seq; Type: SEQUENCE SET; Schema: public; Owner: stc_admin
--

SELECT pg_catalog.setval('public.pipe_module_id_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: stc_admin
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- Name: execution execution_log_pkey; Type: CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.execution
    ADD CONSTRAINT execution_log_pkey PRIMARY KEY (id);


--
-- Name: execution_module execution_module_pkey; Type: CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.execution_module
    ADD CONSTRAINT execution_module_pkey PRIMARY KEY (id);


--
-- Name: module module_type_pkey; Type: CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.module
    ADD CONSTRAINT module_type_pkey PRIMARY KEY (id);


--
-- Name: pipe_module pipe_module_type_pkey; Type: CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.pipe_module
    ADD CONSTRAINT pipe_module_type_pkey PRIMARY KEY (id);


--
-- Name: pipe pipe_pkey; Type: CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.pipe
    ADD CONSTRAINT pipe_pkey PRIMARY KEY (id);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: executionlog_pipeid_fkey; Type: INDEX; Schema: public; Owner: stc_admin
--

CREATE INDEX executionlog_pipeid_fkey ON public.execution USING btree (pipe_id);


--
-- Name: executionlog_userid_fkey; Type: INDEX; Schema: public; Owner: stc_admin
--

CREATE INDEX executionlog_userid_fkey ON public.execution USING btree (user_id);


--
-- Name: executionmodule_executionlogid_fkey; Type: INDEX; Schema: public; Owner: stc_admin
--

CREATE INDEX executionmodule_executionlogid_fkey ON public.execution_module USING btree (execution_id);


--
-- Name: pipe_userid_fkey; Type: INDEX; Schema: public; Owner: stc_admin
--

CREATE INDEX pipe_userid_fkey ON public.pipe USING btree (user_id);


--
-- Name: pipemoduletype_moduletypeid_fkey; Type: INDEX; Schema: public; Owner: stc_admin
--

CREATE INDEX pipemoduletype_moduletypeid_fkey ON public.pipe_module USING btree (module_id);


--
-- Name: pipemoduletype_pipeid_fkey; Type: INDEX; Schema: public; Owner: stc_admin
--

CREATE INDEX pipemoduletype_pipeid_fkey ON public.pipe_module USING btree (pipe_id);


--
-- Name: users_username_idx; Type: INDEX; Schema: public; Owner: stc_admin
--

CREATE UNIQUE INDEX users_username_idx ON public.users USING btree (username);


--
-- Name: execution fk_execution_pipe; Type: FK CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.execution
    ADD CONSTRAINT fk_execution_pipe FOREIGN KEY (pipe_id) REFERENCES public.pipe(id) ON DELETE CASCADE;


--
-- Name: execution fk_execution_user; Type: FK CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.execution
    ADD CONSTRAINT fk_execution_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: execution_module fk_executionmodule_execution; Type: FK CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.execution_module
    ADD CONSTRAINT fk_executionmodule_execution FOREIGN KEY (execution_id) REFERENCES public.execution(id) ON DELETE CASCADE;


--
-- Name: pipe fk_pipe_user; Type: FK CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.pipe
    ADD CONSTRAINT fk_pipe_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: pipe_module fk_pipemodule_module; Type: FK CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.pipe_module
    ADD CONSTRAINT fk_pipemodule_module FOREIGN KEY (module_id) REFERENCES public.module(id);


--
-- Name: pipe_module fk_pipemodule_pipe; Type: FK CONSTRAINT; Schema: public; Owner: stc_admin
--

ALTER TABLE ONLY public.pipe_module
    ADD CONSTRAINT fk_pipemodule_pipe FOREIGN KEY (pipe_id) REFERENCES public.pipe(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

