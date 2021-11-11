ALTER TABLE product ADD COLUMN category_id integer REFERENCES category (id);

UPDATE product
   SET category_id = 1;