create table users
      (
       id                    bigserial,
       username              varchar(30) not null unique,
       password              varchar(80) not null,
       email                 varchar(50) unique,
       primary key (id)
      );

create table roles
      (
       id                    bigserial,
       name                  varchar(50) not null,
       primary key (id)
      );

create table user_role
      (
       user_id               bigint not null,
       role_id               bigint not null,
       primary key (user_id, role_id),
       foreign key (user_id) references users (id),
       foreign key (role_id) references roles (id)
      );

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com');

insert into user_role (user_id, role_id)
values (1, 1),
       (1, 2);