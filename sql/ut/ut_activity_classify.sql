create table activity_classify
(
    id          bigint      not null,
    cname       varchar(35) not null comment '分类名',
    create_time datetime    not null,
    update_time datetime    not null,
    deleted     tinyint     not null,
    constraint activity_classify_id_uindex
        unique (id)
)
    comment '活动分类';

alter table activity_classify
    add primary key (id);

INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1, '测试', '2021-03-10 14:02:55', '2021-03-10 14:02:58', 0);
INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1369592871478923265, '测试分类2', '2021-03-10 18:16:01', '2021-03-10 18:16:01', 0);
INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1369594002036834306, '测试分类4', '2021-03-10 18:20:31', '2021-03-10 18:21:36', 0);
INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1369594533488705538, 'awdawdawdawdawd', '2021-03-10 18:22:37', '2021-03-10 18:22:37', 1);
INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1369902315752431617, '测试分类1', '2021-03-11 14:45:38', '2021-03-11 14:45:38', 0);
INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1369902360350466050, '测试分类3', '2021-03-11 14:45:49', '2021-03-11 14:45:49', 0);
INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1369902386044772353, '测试分类5', '2021-03-11 14:45:55', '2021-03-11 14:45:55', 0);
INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1369902396740247553, '测试分类6', '2021-03-11 14:45:58', '2021-03-11 14:45:58', 0);
INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1369902408694013953, '测试分类77', '2021-03-11 14:46:00', '2021-03-11 15:31:53', 1);
INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1369902427559993345, '测试分类8', '2021-03-11 14:46:05', '2021-03-11 14:46:05', 1);
INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1369917335559270402, '测试分类7', '2021-03-11 15:45:19', '2021-03-11 15:45:19', 0);