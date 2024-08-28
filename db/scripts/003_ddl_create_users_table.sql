create TABLE if not exists users (
   id SERIAL PRIMARY KEY,
   name TEXT not null,
   email TEXT not null unique,
   login TEXT not null,
   password TEXT not null
);