create table if not exists user
(
    email varchar(255) not null primary key,
    firstname varchar(255),
    lastname varchar(255),
    password varchar(255) not null,
    organization_id int not null,
    constraint fk_organization__user foreign key (organization_id) references organization(id)
);