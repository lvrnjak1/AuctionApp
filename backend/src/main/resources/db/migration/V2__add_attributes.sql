create table card_details
(
    id bigint not null constraint card_details_pkey primary key,
    name_on_card varchar(255) not null unique,
    card_number varchar(255) not null unique,
    expiration_month integer not null,
    expiration_year integer not null,
    cvc integer not null
);

alter table users
add column gender varchar(255),
add column date_of_birth timestamp with time zone,
add column phone_number varchar(255),
add column profile_photo_url varchar(255),
add column card_details_id bigint constraint fk_credit_card references card_details;

alter table products add column long_description varchar;
update products set long_description = description;
alter table products drop column description;
alter table products rename long_description to description;

update users
set profile_photo_url = 'https://res.cloudinary.com/lvrnjak/image/upload/v1617811561/pexels-andrea-piacquadio-774909_1_vernbk.jpg'
where id = 1;

update users
set profile_photo_url = 'https://res.cloudinary.com/lvrnjak/image/upload/v1617812253/rsz_pexels-moose-photos-556068_ipnoon.jpg'
where id = 2;

update users
set profile_photo_url = 'https://res.cloudinary.com/lvrnjak/image/upload/v1617812189/rsz_pexels-martin-p%C3%A9chy-2078265_g9myca.jpg'
where id = 3;

update users
set profile_photo_url = 'https://res.cloudinary.com/lvrnjak/image/upload/v1617812118/rsz_pexels-daniel-xavier-1239291_dqnqmy.jpg'
where id = 4;

update users
set profile_photo_url = 'https://res.cloudinary.com/lvrnjak/image/upload/v1617812072/rsz_1rsz_pexels-pixabay-206445_mdfb6n.jpg'
where id = 5;

update users
set profile_photo_url = 'https://res.cloudinary.com/lvrnjak/image/upload/v1617811905/rsz_pexels-pixabay-354951_xrjvzt.jpg'
where id = 6;

update users
set profile_photo_url = 'https://res.cloudinary.com/lvrnjak/image/upload/v1617811669/pexels-tu%E1%BA%A5n-ki%E1%BB%87t-jr-1382731_xq5uvw.jpg'
where id = 7;

update users
set profile_photo_url = 'https://res.cloudinary.com/lvrnjak/image/upload/v1617811649/pexels-pixabay-220453_nyypke.jpg'
where id = 8;

update users
set profile_photo_url = 'https://res.cloudinary.com/lvrnjak/image/upload/v1617811578/pexels-bruno-salvadori-2269872_v1xczt.jpg'
where id = 9;