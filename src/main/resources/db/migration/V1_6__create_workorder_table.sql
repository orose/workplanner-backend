create table if not exists workorder
(
    id int not null auto_increment primary key,
    title varchar(255),
    description varchar(255),
    team_id int,
    organization_id int not null,
    created_at timestamp not null,
    updated_at timestamp,
    created_by int not null,
    updated_by int,
    constraint fk_team__workorder foreign key (team_id) references team(id),
    constraint fk_organization__workorder foreign key (organization_id) references organization(id)
);
