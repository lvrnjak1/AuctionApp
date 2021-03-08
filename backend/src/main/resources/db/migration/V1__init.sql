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
    name varchar(255) not null,
    parent_category_id bigint constraint fk_parent_category references categories,
    image_url varchar(255)
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
    end_date_time timestamp with time zone,
    start_date_time timestamp with time zone,
    start_price numeric(19, 2),
    product_id bigint constraint fk_product_1 references products,
    seller_id bigint constraint fk_seller references users
);

create table images
(
    id bigint not null constraint images_pkey primary key,
    image_url varchar(255) not null,
    product_id bigint constraint fk_product_2 references products
);

create table product_colors
(
    product_id bigint not null constraint fk_product_3 references products,
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

insert into categories (id, name, parent_category_id)
values (1, 'Women', null);
insert into categories (id, name, parent_category_id)
values (2, 'Men', null);
insert into categories (id, name, parent_category_id)
values (3, 'Kids', null);
insert into categories (id, name, parent_category_id)
values (4, 'Home', null);
insert into categories (id, name, parent_category_id)
values (5, 'Art', null);
insert into categories (id, name, parent_category_id)
values (6, 'Electronics', null);
-- subcategories of women
insert into categories (id, name, parent_category_id, image_url)
values (7, 'Accessories', 1, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (8, 'Bags', 1, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254529/categories/woman-bags.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (9, 'Clothes', 1, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254460/categories/women-clothes.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (10, 'Sleepwear', 1, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (11, 'Swimwear', 1, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (12, 'Shoes', 1, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254637/categories/woman-shoes.jpg');
-- subcategories of men
insert into categories (id, name, parent_category_id, image_url)
values (13, 'Accessories', 2, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (14, 'Clothes', 2, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (15, 'Sleepwear', 2, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (16, 'Swimwear', 2, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (17, 'Shoes', 2, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254609/categories/men-shoes.jpg');
-- subcategories of kids
insert into categories (id, name, parent_category_id, image_url)
values (18, 'Toys', 3, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (19, 'Clothes', 3, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (20, 'Sleepwear', 3, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (21, 'Swimwear', 3, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (22, 'Shoes', 3, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254651/categories/kids-shoes.jpg');
-- subcategories of home
insert into categories (id, name, parent_category_id, image_url)
values (23, 'Decor', 4, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254774/categories/home-decor.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (24, 'Furniture', 4, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (25, 'Appliances', 4, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
-- subcategories of electronics
insert into categories (id, name, parent_category_id, image_url)
values (26, 'PC', 6, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (27, 'Phones', 6, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (28, 'Peripheral', 6, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');
insert into categories (id, name, parent_category_id, image_url)
values (29, 'Accessories', 6, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');

insert into products (id, description, name, size, category_id)
values (1, 'Very comfortable high heels. Perfect for a party. Black sandals.', 'Black sandals', 'MEDIUM', 12);
insert into images (id, image_url, product_id)
values (1, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250583/pexels-skylar-kang-6046183_ldznbw.jpg', 1);
insert into images (id, image_url, product_id)
values (2, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250571/pexels-skylar-kang-6046186_yff8db.jpg', 1);
insert into product_colors (product_id, color_id) values (1, 1);
insert into auctions (id, end_date_time, start_date_time, start_price, product_id, seller_id)
values (1, '2021-03-21 11:00:00.000000', '2021-02-25 11:00:00.000000', 150, 1, 1);

insert into products (id, description, name, size, category_id)
values (2, 'Plain shirts in all colors imaginable. Comfortable made of cotton.', 'Plain T-shirt', 'LARGE', 9);
insert into images (id, image_url, product_id)
values (3, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250604/pexels-sorapong-chaipanya-4530802_mb8col.jpg', 2);
insert into images (id, image_url, product_id)
values (4, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250607/pexels-sorapong-chaipanya-4530800_adrfmi.jpg', 2);
insert into product_colors(product_id, color_id) values (2, 1);
insert into product_colors(product_id, color_id) values (2, 2);
insert into product_colors(product_id, color_id) values (2, 3);
insert into product_colors(product_id, color_id) values (2, 4);
insert into product_colors(product_id, color_id) values (2, 5);
insert into product_colors(product_id, color_id) values (2, 6);
insert into product_colors(product_id, color_id) values (2, 7);
insert into product_colors(product_id, color_id) values (2, 8);
insert into product_colors(product_id, color_id) values (2, 9);
insert into product_colors(product_id, color_id) values (2, 10);
insert into auctions (id, end_date_time, start_date_time, start_price, product_id, seller_id)
values (2, '2021-03-22 11:15:00.000000', '2021-03-02 11:30:00.000000', 30, 2, 1);

insert into products (id, description, name, size, category_id)
values (3,
        'Amazing skinny jeans. Stretchy and comfortable. They are high waisted and made of washed out denim',
        'High-waisted jeans',
        'MEDIUM',
        9
);
insert into images (id, image_url, product_id)
values (5, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250773/pexels-karolina-grabowska-4210863_uzc0ub.jpg', 3);
insert into images (id, image_url, product_id)
values (6, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250868/pexels-karolina-grabowska-4210860_yrbjfe.jpg', 3);
insert into images (id, image_url, product_id)
values (7, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250674/pexels-karolina-grabowska-4210866_y08z2f.jpg', 3);
insert into product_colors(product_id, color_id) values (3, 1);
insert into product_colors(product_id, color_id) values (3, 2);
insert into product_colors(product_id, color_id) values (3, 4);
insert into product_colors(product_id, color_id) values (3, 10);
insert into auctions (id, end_date_time, start_date_time, start_price, product_id, seller_id)
values (3, '2021-03-20 13:30:00.000000', '2021-02-24 11:30:00.000000', 65.50, 3, 1);

insert into products (id, description, name, size, category_id)
values (4,
        'Gorgeous red suit with blazer and shorts. It can be dressed up or dressed down.',
        'Casual suit',
        'SMALL',
        9
);
insert into images (id, image_url, product_id)
values (8, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250753/pexels-cottonbro-4937449_mr1daj.jpg', 4);
insert into images (id, image_url, product_id)
values (9, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250798/pexels-cottonbro-4937448_acxl8s.jpg', 4);
insert into product_colors(product_id, color_id) values (4, 4);
insert into auctions (id, end_date_time, start_date_time, start_price, product_id, seller_id)
values (4, '2021-03-19 11:30:00.000000', '2021-02-26 11:30:00.000000', 140.50, 4, 1);

insert into products (id, description, name, size, category_id)
values (5,
        'Practical handmade circular bags. Made of sustainable materials. Colorful and modern.',
        'Handmade bag',
        'NOT_APPLICABLE',
        8
);
insert into images (id, image_url, product_id)
values (10, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250871/pexels-artem-beliaikin-1117272_vbhrbl.jpg', 5);
insert into images (id, image_url, product_id)
values (11, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250869/pexels-artem-beliaikin-1117294_ux180y.jpg', 5);
insert into images (id, image_url, product_id)
values (12, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250862/pexels-artem-beliaikin-1253189_vtfivt.jpg', 5);
insert into product_colors(product_id, color_id) values (5, 1);
insert into product_colors(product_id, color_id) values (5, 2);
insert into product_colors(product_id, color_id) values (5, 6);
insert into auctions (id, end_date_time, start_date_time, start_price, product_id, seller_id)
values (5, '2021-03-29 11:30:00.000000', '2021-02-18 11:30:00.000000', 55, 5, 1);

insert into products (id, description, name, size, category_id)
values (6,
        'Comfortable shoes for running and training. Not waterproof. Feels like walking on clouds.',
        'Running shoes',
        'LARGE',
        12
);
insert into images (id, image_url, product_id)
values (13, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250671/pexels-melvin-buezo-2529148_r7gota.jpg', 6);
insert into product_colors(product_id, color_id) values (6, 1);
insert into product_colors(product_id, color_id) values (6, 2);
insert into product_colors(product_id, color_id) values (6, 9);
insert into product_colors(product_id, color_id) values (6, 10);
insert into auctions (id, end_date_time, start_date_time, start_price, product_id, seller_id)
values (6, '2021-03-17 12:30:00.000000', '2021-02-25 10:30:00.000000', 240, 6, 1);

insert into products (id, description, name, size, category_id)
values (7,
        'Wool sweaters perfect for fall and winter. Not itchy, long sleeve and comfy.',
        'Wool sweaters',
        'MEDIUM',
        9
);
insert into images (id, image_url, product_id)
values (14, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250729/pexels-daria-shevtsova-1030946_hgk9kh.jpg', 7);
values (15, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250659/pexels-tijana-drndarski-5475173_woojgg.jpg', 7);
insert into product_colors(product_id, color_id) values (7, 1);
insert into product_colors(product_id, color_id) values (7, 2);
insert into product_colors(product_id, color_id) values (7, 5);
insert into product_colors(product_id, color_id) values (7, 10);
insert into auctions (id, end_date_time, start_date_time, start_price, product_id, seller_id)
values (7, '2021-03-27 12:30:00.000000', '2021-02-25 13:30:00.000000', 25, 7, 1);

insert into products (id, description, name, size, category_id)
values (8,
        'Hiking backpack that can fit a lot of things. Ideal for long trips.',
        'Hiking backpack',
        'LARGE',
        13
);
insert into images (id, image_url, product_id)
values (16, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250598/pexels-ravindra-rawat-1294731_wjiev6.jpg', 8);
insert into product_colors(product_id, color_id) values (8, 4);
insert into auctions (id, end_date_time, start_date_time, start_price, product_id, seller_id)
values (8, '2021-03-22 15:30:00.000000', '2021-02-26 09:30:00.000000', 25, 8, 1);