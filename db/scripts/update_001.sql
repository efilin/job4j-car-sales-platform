CREATE TABLE seller (
  id       serial PRIMARY KEY,
  name     VARCHAR(2000),
  username VARCHAR(2000),
  password VARCHAR(2000),
  role     VARCHAR(2000),
  phone    BIGINT,
  active   BOOLEAN
);

CREATE TABLE s_car (
  id              serial PRIMARY KEY,
  manufacturer    VARCHAR(100),
  model           VARCHAR(100),
  mileage         INTEGER,
  price           INTEGER,
  production_year INTEGER,
  car_body        VARCHAR(100),
  engine          VARCHAR(100),
  transmission    VARCHAR(100),
  description     VARCHAR(100),
  photo           VARCHAR(300),
  seller_id       integer references seller (id),
  on_sale         BOOLEAN,
  created         TIMESTAMP
);

