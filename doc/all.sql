drop table if exists test;

create table test (
    id bigint not null comment 'id',
    name varchar(50) comment 'name',
    password varchar(50) comment 'passowr',
    primary key (id)
) engine = innodb default
charset = utf8mb4 comment='test';
