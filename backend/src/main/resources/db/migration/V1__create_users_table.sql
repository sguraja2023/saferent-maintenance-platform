create table users (
    id bigserial primary key,
    full_name varchar(150) not null,
    email varchar(255) not null unique,
    password_hash varchar(255) not null,
    role varchar(50) not null,
    created_at timestamptz not null default now()
);
