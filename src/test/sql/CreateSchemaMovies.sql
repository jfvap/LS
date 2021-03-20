drop table if exists reviews;
drop table if exists movies;
drop table if exists users;

CREATE TABLE users (
  id serial PRIMARY KEY,
  name varchar(200),
  email varchar(80),
  CONSTRAINT constraint_unique_users_email UNIQUE (email)
);

CREATE TABLE movies (
  id serial PRIMARY KEY,
  title varchar(80),
  releaseYear smallint,
  rating1 smallint DEFAULT 0,
  rating2 smallint DEFAULT 0,
  rating3 smallint DEFAULT 0,
  rating4 smallint DEFAULT 0,
  rating5 smallint DEFAULT 0,
  UNIQUE(title, releaseYear)
);

CREATE TABLE reviews (
  id serial PRIMARY KEY,
  mid int REFERENCES movies (id) NOT NULL,
  uid int REFERENCES users (id),--nao é obrigatorio estar assoc a um user
  summary varchar(80),
  review varchar(1024),
  rating int,
  CONSTRAINT constraint_review_rating_value CHECK (rating in (1,2,3,4,5))
  --(rating > 0 and rating < 6),-- --  UNIQUE (id, mid)
);
