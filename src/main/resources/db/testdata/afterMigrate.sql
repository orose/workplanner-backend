SET foreign_key_checks = 0;
truncate table permission;
truncate table organization;
truncate table team;
truncate table user;
truncate table user_team;
truncate table invite;
SET foreign_key_checks = 1;

insert into permission (permission_key, description) values ('ADMIN', 'Administrator permission');
insert into organization (name, email, organization_number) values ('Organization name', 'email@organization.com', 123456);
insert into invite values ('user@email.com', 1);