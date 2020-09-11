create table user_workorder (
  user_id int not null,
  workorder_id int not null,

  primary key(user_id, workorder_id),
  constraint fk_user_workorder__workorder foreign key (workorder_id) references workorder (id),
  constraint fk_user_workorder__user foreign key (user_id) references application_user (id)
);

