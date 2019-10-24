create table if not exists workorder
(
    id int not null auto_increment primary key,
    title varchar(255),
    description varchar(255),
    team_id int,
    organization_id int not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    created_by int,
    updated_by int,
    constraint fk_team__workorder foreign key (team_id) references team(id),
    constraint fk_organization__workorder foreign key (organization_id) references organization(id)
);
