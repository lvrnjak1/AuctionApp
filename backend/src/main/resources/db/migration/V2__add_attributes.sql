alter table users
    add column gender varchar(255),
    add column date_of_birth timestamp with time zone,
    add column phone_number varchar(255),
    add column profile_photo_url varchar(255);