create table if not exists workorder
(
    id int not null auto_increment primary key,
    title varchar(255),
    description varchar(255),
    team_id int not null,
    constraint fk_team__workorder foreign key (team_id) references team(id)
);