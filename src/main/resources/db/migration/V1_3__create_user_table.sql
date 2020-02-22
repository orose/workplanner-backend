create table if not exists user
(
    id int not null auto_increment primary key,
    email varchar(255) not null unique,
    firstname varchar(255),
    lastname varchar(255),
    password varchar(255) not null,
    organization_id int not null,
    constraint fk_organization__user foreign key (organization_id) references organization(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;