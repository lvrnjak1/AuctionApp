drop table if exists product_colors;
drop table if exists images;
drop table if exists auctions;
drop table if exists products;
drop table if exists colors;
drop table if exists categories;
drop table if exists user_roles;
drop table if exists roles;
drop table if exists users;

create table roles
(
    id bigserial not null constraint roles_pkey primary key,
    role varchar(255) constraint uk_roles_role unique
);

create table users
(
    id bigserial not null constraint users_pkey primary key,
    email varchar(255) not null constraint uk_users_email unique,
    name varchar(255) not null,
    password varchar(255),
    surname varchar(255) not null
);

create table user_roles
(
    user_id bigint not null constraint fk_user references users,
    role_id bigint not null constraint fk_role references roles,
    constraint user_roles_pkey primary key (user_id, role_id)
);

create table categories
(
    id bigint not null constraint categories_pkey primary key,
    c_left integer not null,
    c_right integer not null,
    name varchar(255) not null
);

create table colors
(
    id bigint not null constraint colors_pkey primary key,
    color varchar(255) constraint uk_colors_color unique
);

create table products
(
    id bigint not null constraint products_pkey primary key,
    description varchar(255),
    name varchar(255) not null,
    size varchar(255),
    category_id bigint constraint fk_category references categories
);

create table auctions
(
    id bigint not null constraint auctions_pkey primary key,
    end_date_time timestamp,
    start_date_time timestamp,
    start_price numeric(19, 2),
    product_id bigint constraint fk_product references products,
    seller_id bigint constraint fk_user references users
);

create table images
(
    id bigint not null constraint images_pkey primary key,
    image oid not null,
    product_id bigint constraint fk_product references products
);

create table product_colors
(
    product_id bigint not null constraint fk_product references products,
    color_id bigint not null constraint fk_color references colors,
    constraint product_colors_pkey primary key (product_id, color_id)
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
insert into user_roles (user_id, role_id) values (1,2);

insert into colors (id, color) values (1, 'BLACK');
insert into colors (id, color) values (2, 'WHITE');
insert into colors (id, color) values (3, 'RED');
insert into colors (id, color) values (4, 'BLUE');
insert into colors (id, color) values (5, 'GREEN');
insert into colors (id, color) values (6, 'ORANGE');
insert into colors (id, color) values (7, 'PINK');
insert into colors (id, color) values (8, 'PURPLE');
insert into colors (id, color) values (9, 'YELLOW');
insert into colors (id, color) values (10, 'GRAY');

