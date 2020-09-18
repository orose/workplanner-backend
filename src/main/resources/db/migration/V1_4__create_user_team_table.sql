create table user_team (
  user_id int not null,
  team_id int not null,
  permission_key varchar(20) not null,

  primary key(user_id, team_id),
  constraint fk_user_team__team foreign key (team_id) references team (id),
  constraint fk_user_team__user foreign key (user_id) references application_user (id),
  constraint fk_user_team__permission foreign key (permission_key) references permission (permission_key)
);