create table if not exists user
(
    email varchar(255) not null primary key,
    firstname varchar(255),
    lastname varchar(255),
    password varchar(255) not null,
    constraint uc_email unique (email)
) default CHARSET=UTF8MB4;