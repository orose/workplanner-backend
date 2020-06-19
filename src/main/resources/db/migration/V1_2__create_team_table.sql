create table if not exists team
(
    id serial primary key,
    name varchar(255) not null,
    organization_id int not null,
    constraint fk_organization__team foreign key (organization_id) references organization(id)
);