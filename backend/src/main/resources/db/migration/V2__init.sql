drop sequence if exists hibernate_sequence;
drop table if exists roles cascade;
drop table if exists user_roles cascade;
drop table if exists users cascade;

create sequence if not exists hibernate_sequence minvalue 100;

create table roles
(
    id   bigserial not null
        constraint roles_pkey
            primary key,
    role varchar(255)
        constraint uk_nb4h0p6txrmfc0xbrd1kglp9t
            unique
);

create table users
(
    id       bigserial    not null
        constraint users_pkey
            primary key,
    email    varchar(255) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    name     varchar(255) not null,
    password varchar(255),
    surname  varchar(255) not null
);

create table user_roles
(
    user_id bigint not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references users,
    role_id bigint not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles,
    constraint user_roles_pkey
        primary key (user_id, role_id)
);

insert into roles(id, role) values (1, 'ROLE_BUYER');
insert into roles(id, role) values (2, 'ROLE_SELLER');

insert into users(id, email, name, surname, password)
values (1, 'lamija.vrnjak@gmail.com', 'Lamija', 'Vrnjak', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');
insert into users(id, email, name, surname, password)
values (2, 'miki.maus@gmail.com', 'Miki', 'Maus', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');
insert into users(id, email, name, surname, password)
values (3, 'paja.patak@gmail.com', 'Paja', 'Patak', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');

insert into user_roles (user_id, role_id) values (1,1);
insert into user_roles (user_id, role_id) values (2,1);
insert into user_roles (user_id, role_id) values (3,1);