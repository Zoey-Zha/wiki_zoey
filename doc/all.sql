drop table if exists test;

create table test (
    id bigint not null comment 'id',
    name varchar(50) comment 'name',
    password varchar(50) comment 'passowr',
    primary key (id)
) engine = innodb default
charset = utf8mb4 comment='test';

drop table if exists demo;


------
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