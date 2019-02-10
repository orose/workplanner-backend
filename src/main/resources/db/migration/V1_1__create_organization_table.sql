create table if not exists organization
(
    id int not null auto_increment primary key,
    name varchar(255) not null,
    email varchar(255),
    organization_number varchar(255)
);