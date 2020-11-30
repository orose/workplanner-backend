truncate permission,
organization,
team,
application_user,
user_team,
invite,
workorder,
user_workorder restart identity;

insert into permission (permission_key, description) values ('ADMIN', 'Administrator permission');
insert into organization (name, email, organization_number) values ('Organization name', 'email@organization.com', 123456);
insert into application_user (email, firstname, lastname, password, organization_id) values ('test@email.com', 'firstname', 'lastname', '$2a$10$Bcw8mEiOVyzzI537dhRv9.V1OHEIdWSB/v0UozGP.d2Td8PYwUjKy', 1);

insert into invite values ('user@email.com', 1);

insert into workorder (title, description, status, organization_id, created_at, created_by, updated_at, updated_by)
values
('title', 'description', 'NEW', 1, now(), 1, now(), 1);

insert into organization (name, email, organization_number) values ('Organization name2', 'email@organization2.com', 246810);
