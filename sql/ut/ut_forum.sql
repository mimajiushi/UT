create table forum
(
    id          bigint unsigned auto_increment
        primary key,
    name        varchar(50)                   not null comment '版块名称',
    create_time datetime                      not null,
    update_time datetime                      not null on update CURRENT_TIMESTAMP,
    deleted     tinyint(2) unsigned default 0 not null
)
    comment 'bbs的版块分类';

create index idx_id_name
    on forum (id, name);

INSERT INTO ut.forum (id, name, create_time, update_time, deleted) VALUES (1, '校园八卦', '2020-05-19 19:10:43', '2020-05-19 19:10:44', 0);
INSERT INTO ut.forum (id, name, create_time, update_time, deleted) VALUES (2, '每日一水', '2020-05-19 19:10:43', '2020-05-19 19:10:44', 0);
INSERT INTO ut.forum (id, name, create_time, update_time, deleted) VALUES (3, '学习', '2020-05-19 19:10:43', '2020-05-19 19:11:34', 0);
INSERT INTO ut.forum (id, name, create_time, update_time, deleted) VALUES (4, '游戏', '2020-05-19 19:10:43', '2020-05-19 19:10:44', 0);
INSERT INTO ut.forum (id, name, create_time, update_time, deleted) VALUES (5, '测试版块', '2020-05-19 20:38:17', '2020-05-19 20:42:49', 1);
INSERT INTO ut.forum (id, name, create_time, update_time, deleted) VALUES (6, 'test', '2020-05-21 12:25:33', '2020-05-21 12:25:00', 1);