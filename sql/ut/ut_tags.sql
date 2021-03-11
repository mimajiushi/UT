create table tags
(
    id          bigint               not null
        primary key,
    parent_id   bigint     default 0 not null,
    name        varchar(50)          not null,
    level       tinyint(2) default 0 not null comment 'level 0 is top',
    deleted     tinyint(2)           not null,
    create_time datetime             not null,
    update_time datetime             not null on update CURRENT_TIMESTAMP
)
    charset = utf8mb4;

create index idx_id_name
    on tags (id, name);

INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (1, 0, '技术', 0, 0, '2020-03-11 13:04:03', '2020-04-13 23:13:59');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (2, 1, 'Java', 1, 0, '2020-03-11 13:04:34', '2020-04-13 23:14:01');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (3, 1, 'PHP', 1, 0, '2020-03-11 13:04:53', '2020-04-13 23:14:01');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (4, 1, 'web前端2', 1, 0, '2020-03-11 13:05:16', '2020-04-13 22:57:51');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (5, 1, '算法工程师', 1, 0, '2020-03-11 13:05:40', '2020-02-25 13:46:02');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (6, 0, '金融', 0, 0, '2020-03-11 13:06:13', '2020-02-25 13:46:25');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (7, 6, '理财', 1, 0, '2020-03-11 13:06:43', '2020-02-25 08:14:18');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (8, 6, '炒股', 1, 0, '2020-03-11 13:07:17', '2020-02-25 08:14:50');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (13, 0, '产品', 0, 0, '2020-03-11 20:29:41', '2020-02-25 13:46:43');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (14, 13, '产品经理', 1, 0, '2020-03-11 20:30:04', '2020-02-25 13:47:08');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (15, 13, '产品总监', 1, 0, '2020-03-11 20:30:22', '2020-02-25 13:47:27');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (16, 13, '数据产品经理', 1, 0, '2020-03-11 20:30:38', '2020-02-25 13:47:45');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (17, 0, '设计', 0, 0, '2020-03-11 20:30:54', '2020-02-25 13:49:29');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (18, 17, 'UI设计师', 1, 0, '2020-03-11 20:31:08', '2020-02-25 13:51:14');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (19, 17, '平面设计师', 1, 0, '2020-03-11 20:31:23', '2020-02-25 13:51:12');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (20, 17, '交互设计师', 1, 0, '2020-03-11 20:31:44', '2020-02-25 13:51:09');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (21, 0, '运营', 0, 0, '2020-03-11 20:32:00', '2020-02-25 13:49:27');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (22, 21, '新媒体运营', 1, 0, '2020-03-11 20:32:41', '2020-02-25 13:49:58');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (23, 21, '产品运营', 1, 0, '2020-03-11 20:33:06', '2020-02-25 13:50:25');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (24, 21, '网络推广', 1, 0, '2020-03-11 20:33:26', '2020-02-25 13:50:47');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (25, 0, '市场', 0, 0, '2020-03-11 20:33:55', '2020-02-25 13:51:20');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (26, 25, '市场营销', 1, 0, '2020-03-11 20:34:16', '2020-02-25 13:51:44');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (27, 25, '市场推广', 1, 0, '2020-03-11 20:34:37', '2020-02-25 13:52:04');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (28, 25, '品牌公关', 1, 0, '2020-03-11 20:34:57', '2020-02-25 13:52:27');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (29, 25, '策划经理', 1, 0, '2020-03-11 20:35:14', '2020-02-25 13:52:47');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (30, 0, '人事', 0, 0, '2020-03-11 20:35:34', '2020-02-25 13:53:09');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (31, 30, '人事HR', 1, 0, '2020-03-11 20:35:51', '2020-02-25 13:53:26');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (32, 30, '行政', 1, 0, '2020-03-11 20:36:08', '2020-02-25 13:53:48');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (33, 30, '绩效考核', 1, 0, '2020-03-11 20:36:31', '2020-02-25 13:54:11');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (34, 0, 'test1', 1, 1, '2020-03-11 20:36:31', '2020-04-12 20:33:41');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (35, 0, 'test2', 1, 1, '2020-03-11 20:36:31', '2020-04-12 20:33:41');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (36, 0, 'test3', 1, 1, '2020-03-11 20:36:31', '2020-04-13 20:43:33');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (37, 0, 'test4', 1, 1, '2020-03-11 20:36:31', '2020-04-13 20:43:33');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (38, 0, 'test5', 1, 1, '2020-03-11 20:36:31', '2020-04-13 17:30:29');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (39, 0, 'test55', 0, 1, '2020-04-13 18:11:44', '2020-04-13 18:11:33');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (40, 0, 'test5', 0, 1, '2020-04-13 18:15:49', '2020-04-13 18:15:18');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (41, 0, '11', 0, 1, '2020-05-13 16:52:45', '2020-05-13 16:52:11');
INSERT INTO ut.tags (id, parent_id, name, level, deleted, create_time, update_time) VALUES (1369535834615291906, 0, 'awdawd', 0, 1, '2021-03-10 14:29:22', '2021-03-10 14:29:27');