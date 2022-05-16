drop table if exists test;

create table test (
    id bigint not null comment 'id',
    name varchar(50) comment 'name',
    password varchar(50) comment 'passowr',
    primary key (id)
) engine = innodb default
charset = utf8mb4 comment='test';

drop table if exists demo;



create table `demo` (
                        `id` bigint not null comment 'id',
                        `name` varchar(50) comment 'name',
                        primary key (id)
) engine = innodb default
charset = utf8mb4 comment='test';

insert into demo values(1001,'Alix');
insert into demo values(1002,'Zoey');


DROP TABLE IF EXISTS `ebook`;
CREATE TABLE `ebook`  (
                          `id` bigint(20) NOT NULL COMMENT 'id',
                          `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
                          `category1_id` bigint(20) NULL DEFAULT NULL COMMENT '分类1',
                          `category2_id` bigint(20) NULL DEFAULT NULL COMMENT '分类2',
                          `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
                          `cover` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面',
                          `doc_count` int(11) NULL DEFAULT NULL COMMENT '文档数',
                          `view_count` int(11) NULL DEFAULT NULL COMMENT '阅读数',
                          `vote_count` int(11) NULL DEFAULT NULL COMMENT '点赞数',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电子书' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ebook
-- ----------------------------
INSERT INTO ebook (id, name, category1_id, category2_id, description, cover, doc_count, view_count, vote_count) VALUES (1, 'SpringBoot 入门教程', 1, 1, '零基础入门 Java 开发，企业级应用开发最佳首选框架', '/image/springboot.png', 67, 878, 99);
INSERT INTO ebook (id, name, category1_id, category2_id, description, cover, doc_count, view_count, vote_count) VALUES (2, 'Vue 入门教程', 2, 2, '零基础入门 Vue 开发，企业级应用开发最佳首选框架', '/image/vue.png', 23, 3245, 433);
INSERT INTO ebook (id, name, category1_id, category2_id, description, cover, doc_count, view_count, vote_count) VALUES (3, 'Python 入门教程', 3, 3, '零基础入门 Python 开发，企业级应用开发最佳首选框架', '/image/python.png', 54, 432, 432);
INSERT INTO ebook (id, name, category1_id, category2_id, description, cover, doc_count, view_count, vote_count) VALUES (4, 'MySQL 入门教程', 4, 4, '零基础入门 MySQL 开发，企业级应用开发最佳首选框架', '/image/mysql.png', 65, 4235, 427);
INSERT INTO ebook (id, name, category1_id, category2_id, description, cover, doc_count, view_count, vote_count) VALUES (5, 'Oracle 入门教程', 5, 5, '零基础入门 Oracle 开发，企业级应用开发最佳首选框架', '/image/oracle.png', 65, 543, 321);

SELECT * from ebook;

create table if not exists `test` (
                        `id` bigint not null comment 'id',
                        `name` varchar(50) comment '名称',
                        `password` varchar(50) comment '密码',
                        primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='测试';

create table if not exists `doc` (
                       `id` bigint not null comment 'id',
                       `ebook_id` bigint not null default 0 comment '电子书id',
                       `parent` bigint not null default 0 comment '父id',
                       `name` varchar(50) not null comment '名称',
                       `sort` int comment '顺序',
                       `view_count` int default 0 comment '阅读数',
                       `vote_count` int default 0 comment '点赞数',
                       primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='文档';

insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (1, 1, 0, '文档1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (2, 1, 1, '文档1.1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (3, 1, 0, '文档2', 2, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (4, 1, 3, '文档2.1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (5, 1, 3, '文档2.2', 2, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (6, 1, 5, '文档2.2.1', 1, 0, 0);


-- table : category
drop table if exists `category`;
create table if not exists `category` (
                            `id` bigint not null comment 'id',
                            `parent` bigint not null default 0 comment '父id',
                            `name` varchar(50) not null comment '名称',
                            `sort` int comment '顺序',
                            primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='分类';

insert into `category` (id, parent, name, sort) values (100, 000, '前端开发', 100);
insert into `category` (id, parent, name, sort) values (101, 100, 'Vue', 101);
insert into `category` (id, parent, name, sort) values (102, 100, 'HTML & CSS', 102);
insert into `category` (id, parent, name, sort) values (200, 000, 'Java', 200);
insert into `category` (id, parent, name, sort) values (201, 200, '基础应用', 201);
insert into `category` (id, parent, name, sort) values (202, 200, '框架应用', 202);
insert into `category` (id, parent, name, sort) values (300, 000, 'Python', 300);
insert into `category` (id, parent, name, sort) values (301, 300, '基础应用', 301);
insert into `category` (id, parent, name, sort) values (302, 300, '进阶方向应用', 302);
insert into `category` (id, parent, name, sort) values (400, 000, '数据库', 400);
insert into `category` (id, parent, name, sort) values (401, 400, 'MySQL', 401);
insert into `category` (id, parent, name, sort) values (500, 000, '其它', 500);
insert into `category` (id, parent, name, sort) values (501, 500, '服务器', 501);
insert into `category` (id, parent, name, sort) values (502, 500, '开发工具', 502);
insert into `category` (id, parent, name, sort) values (503, 500, '热门服务端语言', 503);

insert into `user` (id, `login_name`, `name`, `password`) values (1, 'test', '测试', 'test');

