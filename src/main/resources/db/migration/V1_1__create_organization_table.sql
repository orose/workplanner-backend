create table if not exists organization
(
    id serial primary key,
    name varchar(255) not null,
    email varchar(255),
    organization_number varchar(255)
);