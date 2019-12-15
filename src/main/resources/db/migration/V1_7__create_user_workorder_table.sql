create table user_workorder (
  user_email varchar(255) not null,
  workorder_id int not null,

  primary key(user_email, workorder_id),
  constraint fk_user_workorder__workorder foreign key (workorder_id) references workorder (id),
  constraint fk_user_workorder__user foreign key (user_email) references user (email)
);