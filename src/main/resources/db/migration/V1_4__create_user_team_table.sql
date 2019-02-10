CREATE TABLE user_team (
  user_email varchar(255) not null,
  team_id int not null,
  permission_key varchar(20) not null,
  primary key (user_email, team_id),
  constraint fk_team foreign key (team_id) references team (id),
  constraint fk_user foreign key (user_email) references user (email),
  constraint fk_permission foreign key (permission_key) references permission (permission_key)
);