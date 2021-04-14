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
add column card_details_id bigint constraint fk_credit_card references card_details,
add column active boolean default true;

alter table products add column long_description varchar;
update products set long_description = description;
alter table products drop column description;
alter table products rename long_description to description;