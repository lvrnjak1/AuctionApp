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

insert into categories (id, name, parent_category_id, image_url)
values (30, 'Paintings', 5, 'https://res.cloudinary.com/lvrnjak/image/upload/v1614254789/categories/category-default.jpg');