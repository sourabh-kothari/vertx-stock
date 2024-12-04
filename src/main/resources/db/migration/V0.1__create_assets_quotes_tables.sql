CREATE TABLE assets
(
  VALUE VARCHAR(255) PRIMARY KEY
);

CREATE TABLE quotes
(
  bid NUMERIC,
  ask NUMERIC,
  last_price NUMERIC,
  volume NUMERIC,
  asset varchar,
  FOREIGN KEY (asset) REFERENCES assets(value),
  CONSTRAINT last_price_is_positive CHECK (last_price > 0),
  CONSTRAINT volume_is_positive_or_zero CHECK (volume >= 0)
);
