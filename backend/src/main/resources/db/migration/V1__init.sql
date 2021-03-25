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

create table bids
(
    id bigint not null constraint bids_pkey primary key,
    amount double precision,
    date_time timestamp with time zone,
    auction_id bigint constraint fk_b_auction references auctions,
    bidder_id  bigint constraint fk_b_user references users
);


insert into roles(id, role) values (1, 'ROLE_BUYER');
insert into roles(id, role) values (2, 'ROLE_SELLER');

insert into users(id, email, name, surname, password)
values (1, 'lamija.vrnjak@gmail.com', 'Lamija', 'Vrnjak', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');
insert into users(id, email, name, surname, password)
values (2, 'miki.maus@gmail.com', 'Miki', 'Maus', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');
insert into users(id, email, name, surname, password)
values (3, 'paja.patak@gmail.com', 'Paja', 'Patak', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');
insert into users(id, email, name, surname, password)
values (4, 'meredith.gray@gmail.com', 'Meredith', 'Gray', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');
insert into users(id, email, name, surname, password)
values (5, 'ellen@gmail.com', 'Ellen', 'E', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');
insert into users(id, email, name, surname, password)
values (6, 'janed@gmail.com', 'Jane', 'Doe', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');
insert into users(id, email, name, surname, password)
values (7, 'maddy99@hotmail.com', 'Maddy', 'Smith', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');
insert into users(id, email, name, surname, password)
values (8, 'sam_smith@yahoo.com', 'Sam', 'Smith', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');
insert into users(id, email, name, surname, password)
values (9, 'santa31@gmail.com', 'Santa', 'Claus', '$2a$10$DXLs8e3zTsfPcYWdYUrh9uM3NgC/IchQsTkZJWL25LDOFUpurCniq');

insert into user_roles (user_id, role_id) values (1,1);
insert into user_roles (user_id, role_id) values (2,1);
insert into user_roles (user_id, role_id) values (3,1);
insert into user_roles (user_id, role_id) values (1,2);
insert into user_roles (user_id, role_id) values (4,1);
insert into user_roles (user_id, role_id) values (5,1);
insert into user_roles (user_id, role_id) values (6,1);
insert into user_roles (user_id, role_id) values (7,1);
insert into user_roles (user_id, role_id) values (8,1);
insert into user_roles (user_id, role_id) values (9,1);
insert into user_roles (user_id, role_id) values (3,2);
insert into user_roles (user_id, role_id) values (5,2);
insert into user_roles (user_id, role_id) values (7,2);

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
values (1, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614893229/product1/bs5_n5q2t5.jpg', 1);
insert into images (id, image_url, product_id)
values (2, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614893228/product1/bs4_x4eley.jpg', 1);
insert into product_colors (product_id, color_id) values (1, 1);
insert into auctions (id, end_date_time, start_date_time, start_price, product_id, seller_id)
values (1, '2021-04-21 11:00:00.000000', '2021-02-25 11:00:00.000000', 150, 1, 1);

insert into products (id, description, name, size, category_id)
values (2, 'Plain shirts in all colors imaginable. Comfortable made of cotton.', 'Plain T-shirt', 'LARGE', 9);
insert into images (id, image_url, product_id)
values (3, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614893807/product2/ts4_hvorwh.jpg', 2);
insert into images (id, image_url, product_id)
values (4, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614893807/product2/ts4_hvorwh.jpg', 2);
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
values (2, '2021-04-10 11:15:00.000000', '2021-03-02 11:30:00.000000', 30, 2, 1);

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
values (3, '2021-04-20 13:30:00.000000', '2021-02-20 11:30:00.000000', 65.50, 3, 7);

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
values (4, '2021-04-19 11:30:00.000000', '2021-02-26 11:30:00.000000', 140.50, 4, 1);

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
values (5, '2021-04-15 17:30:00.000000', '2021-02-27 11:30:00.000000', 55, 5, 3);

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
values (6, '2021-04-17 12:30:00.000000', '2021-02-25 10:30:00.000000', 240, 6, 1);

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
values (7, '2021-04-27 12:30:00.000000', '2021-02-25 13:30:00.000000', 25, 7, 5);

insert into products (id, description, name, size, category_id)
values (8,
        'Hiking backpack that can fit a lot of things. Ideal for long trips.',
        'Hiking backpack',
        'LARGE',
        13
);
insert into images (id, image_url, product_id)
values (16, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614896321/pexels-pixabay-532803_txfapi.jpg', 8);
insert into product_colors(product_id, color_id) values (8, 4);
insert into auctions (id, end_date_time, start_date_time, start_price, product_id, seller_id)
values (8, '2021-04-16 10:30:00.000000', '2021-02-26 09:30:00.000000', 25, 8, 3);

insert into images (id, image_url, product_id)
values (17, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614893227/product1/bs3_ilrzdo.jpg', 1);
insert into images (id, image_url, product_id)
values (18, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614893227/product1/bs2_z4aqek.jpg', 1);
insert into images (id, image_url, product_id)
values (19, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614893224/product1/bs1_sujfko.jpg', 1);

insert into images (id, image_url, product_id)
values (20, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614893808/product2/ts1_avkvz7.jpg', 2);
insert into images (id, image_url, product_id)
values (21, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614893809/product2/ts2_bhizaf.jpg', 2);
insert into images (id, image_url, product_id)
values (22, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614893811/product2/ts3_gkbp14.jpg', 2);

insert into images (id, image_url, product_id)
values (23, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894073/hw1_k3ywod.jpg', 3);

insert into images (id, image_url, product_id)
values (24, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894182/rs1_xfko96.webp', 4);

insert into images (id, image_url, product_id)
values (25, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894291/hb1_jcsbvq.jpg', 5);
insert into images (id, image_url, product_id)
values (26, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894290/hb2_phfewd.jpg', 5);

insert into images (id, image_url, product_id)
values (27, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894490/rs2_m6bm7o.jpg', 6);
insert into images (id, image_url, product_id)
values (28, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894488/rs3_gs55wo.jpg', 6);
insert into images (id, image_url, product_id)
values (29, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894490/rs1_wfod14.jpg', 6);
insert into images (id, image_url, product_id)
values (30, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250846/pexels-aman-jakhar-1124466_htyqfc.jpg', 6);
insert into images (id, image_url, product_id)
values (31, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614250716/pexels-styves-exantus-6412694_relxwd.jpg', 6);

insert into images (id, image_url, product_id)
values (32, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894696/ws2_oswequ.jpg', 7);
insert into images (id, image_url, product_id)
values (33, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894693/ws3_ys7o90.jpg', 7);
insert into images (id, image_url, product_id)
values (34, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894702/ws1_lxwifm.jpg', 7);

insert into images (id, image_url, product_id)
values (35, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894846/hbp2_tm5a2w.jpg', 8);
insert into images (id, image_url, product_id)
values (36, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894845/hbp1_mnrkbt.jpg', 8);
insert into images (id, image_url, product_id)
values (37, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894843/hbp3_jdkqrm.png', 8);
insert into images (id, image_url, product_id)
values (38, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614894840/hbp4_mk8wgi.webp', 8);

--bids for 1
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (1, 155, '2021-02-27 21:24:45.808000', 1, 2);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (2, 157, '2021-02-27 22:55:45.808000', 1, 4);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (3, 165.50, '2021-02-28 10:30:45.808000', 1, 7);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (4, 170, '2021-03-01 15:30:00.808000', 1, 4);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (14, 171, '2021-03-02 21:24:45.808000', 1, 9);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (15, 175, '2021-03-02 22:55:45.808000', 1, 3);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (16, 179, '2021-03-05 10:30:45.808000', 1, 7);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (17, 183, '2021-03-08 15:30:00.808000', 1, 4);
--bids for 2
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (5, 33, '2021-02-05 21:24:45.808000', 2, 6);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (6, 35, '2021-02-10 22:55:45.808000', 2, 3);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (7, 40, '2021-02-11 10:30:45.808000', 2, 8);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (8, 42, '2021-02-25 15:30:00.808000', 2, 5);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (9, 44, '2021-03-03 10:30:45.808000', 2, 8);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (10, 45, '2021-03-04 21:24:45.808000', 2, 6);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (11, 50.25, '2021-03-10 22:55:45.808000', 2, 3);
--bids for 3
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (12, 65, '2021-03-07 21:24:45.808000', 3, 4);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (13, 69, '2021-03-08 22:55:45.808000', 3, 9);
--bids for 6
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (18, 242, '2021-03-01 21:24:45.808000', 6, 4);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (19, 244, '2021-03-03 22:55:45.808000', 6, 9);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (20, 245, '2021-03-08 22:55:45.808000', 6, 4);
--bids for 7
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (21, 26, '2021-02-25 21:24:45.808000', 7, 3);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (22, 30, '2021-03-03 22:55:45.808000', 7, 6);
insert into bids (id, amount, date_time, auction_id, bidder_id)
values (23, 35.55, '2021-03-05 22:55:45.808000', 7, 1);