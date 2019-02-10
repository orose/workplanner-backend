create table if not exists team
(
    id int not null auto_increment primary key,
    name varchar(255) not null,
    organization_id int,
    constraint fk_organization__team foreign key (organization_id) references organization(id)
) default CHARSET=UTF8MB4;