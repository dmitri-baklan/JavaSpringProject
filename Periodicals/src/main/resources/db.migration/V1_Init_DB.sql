CREATE TABLE IF NOT EXISTS public.users
(
    id bigserial NOT NULL,
    email varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    surname varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(255) NOT NULL,
    is_active boolean NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS public.periodicals
(
    id bigserial NOT NULL,
    name varchar(255) NOT NULL,
    subject varchar(255) NOT NULL,
    price int8 NOT NULL,
    subscribers int8 NOT NULL,
    CONSTRAINT periodicals_pkey PRIMARY KEY (id)
    CONSTRAINT name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS public.accounts
(
    id bigserial NOT NULL,
    balance int8 NOT NULL,
    user_id int8 NOT NULL,
    CONSTRAINT accounts_pkey PRIMARY KEY (id),
    CONSTRAINT account_owner FOREIGN KEY (user_id)
    REFERENCES public.users (id)
    );


CREATE TABLE IF NOT EXISTS public.replenishments
(
    id bigserial NOT NULL,
    sum int8 NOT NULL,
    account_id int8 NOT NULL,
    time timestamp NOT NULL,
    CONSTRAINT "replenishments_pkey" PRIMARY KEY (id),
    CONSTRAINT account_id FOREIGN KEY (account_id)
    REFERENCES public.accounts
    );



