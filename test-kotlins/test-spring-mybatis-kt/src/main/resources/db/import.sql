BEGIN;

DROP TABLE IF EXISTS goods;

CREATE TABLE IF NOT EXISTS goods (
  id          VARCHAR(32) PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  price       DECIMAL(10, 2),

  amount      INT,
  lock_amount INT DEFAULT 0,
  created_at  DATETIME     NOT NULL, -- http://stackoverflow.com/questions/409286/should-i-use-field-datetime-or-timestamp
  updated_at  DATETIME
);


INSERT INTO goods (id, name, price, amount, created_at) VALUES ('1', '清蒸多宝鱼', 68.42, 25, '2017-01-07');

COMMIT;
