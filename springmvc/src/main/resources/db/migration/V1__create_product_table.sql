CREATE TABLE product
      (
       id     serial                  primary key
      ,title  varchar(255)   not null
      ,cost   numeric(19, 2) not null
      );
