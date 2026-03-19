-- TODO schema
-- example
create table members
(
    member_id                 bigint primary key      not null auto_increment,
    name               varchar(50)             not null,
    password    varchar(255) not null,
    role    varchar(20) not null,
    created_date       timestamp default now() not null,
    last_modified_date timestamp
);

create table contents
(
    content_id bigint primary key not null auto_increment,
    member_id bigint not null,
    title varchar(100) not null,
    description clob not null,
    view_count bigint not null,
    created_date timestamp default now() not null ,
    created_by varchar(50) not null,
    last_modified_date timestamp,
    last_modified_by varchar(50),
    constraint fk_contents_member
        foreign key (member_id) references members(member_id)
)

