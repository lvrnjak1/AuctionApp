create table card_details
(
    id bigint not null constraint card_details_pkey primary key,
    name_on_card varchar(255) not null unique,
    card_number varchar(255) not null unique,
    expiration_month integer not null,
    expiration_year integer not null,
    cvc integer not null
);

alter table users add gender varchar(255);
alter table users add date_of_birth timestamp with time zone;
alter table users add phone_number varchar(255);
alter table users add profile_photo_url varchar(255);
alter table users add card_details_id bigint constraint fk_credit_card references card_details;
alter table users add active boolean default true;

alter table products add long_description varchar;
update products set long_description = description;
alter table products drop column description;
alter table products rename column long_description to description;