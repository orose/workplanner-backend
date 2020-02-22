create table if not exists invite
(
    email varchar(255) not null primary key,
    organization_id int not null,
    constraint fk_organization__invite foreign key (organization_id) references organization(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;