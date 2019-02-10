create table permission (
  permission_key varchar(20) not null primary key,
  description varchar(255)
);

insert into permission (permission_key, description) values ('ADMIN', 'Administrator permission');