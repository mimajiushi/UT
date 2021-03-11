create table activity_collect
(
    id          bigint unsigned auto_increment
        primary key,
    uid         bigint unsigned               not null,
    activity_id bigint unsigned               not null,
    create_time datetime                      not null,
    update_time datetime                      not null on update CURRENT_TIMESTAMP,
    deleted     tinyint(2) unsigned default 0 not null
);

create index idx_uid_activityid
    on activity_collect (uid, activity_id);

INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (21, 2, '2020-05-17 16:15:09', '2020-05-17 16:15:32', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (21, 2, '2020-05-17 16:18:47', '2020-05-17 17:19:33', 0);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (21, 3, '2020-05-17 16:18:47', '2020-05-17 17:19:33', 0);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (213, 2, '2020-05-31 07:29:53', '2020-05-30 23:31:58', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (213, 2, '2020-05-31 07:31:17', '2020-05-30 23:32:06', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (213, 2, '2020-05-31 07:31:45', '2020-05-30 23:35:09', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (213, 4, '2020-05-31 20:28:18', '2020-05-31 19:15:31', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (213, 3, '2020-05-31 20:28:26', '2020-05-31 19:15:45', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (213, 4, '2020-06-02 02:59:04', '2020-06-07 20:34:02', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (21, 4, '2020-06-05 12:52:12', '2020-06-05 12:52:12', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (214, 4, '2020-06-05 13:45:37', '2020-06-05 13:45:36', 0);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (214, 10, '2020-06-05 13:46:46', '2020-06-05 13:46:47', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (214, 3, '2020-06-05 13:46:54', '2020-06-05 13:46:53', 0);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (214, 8, '2020-06-05 15:15:52', '2020-06-05 15:15:53', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (214, 13, '2020-06-06 16:39:12', '2020-06-06 16:43:19', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (21, 14, '2020-06-28 11:00:39', '2020-06-28 11:01:26', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (1367497062361047042, 13, '2021-03-05 00:11:01', '2021-03-05 00:11:02', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (1367497062361047042, 14, '2021-03-05 00:11:16', '2021-03-05 00:30:42', 1);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (1367497062361047042, 13, '2021-03-05 00:30:34', '2021-03-05 00:30:34', 0);
INSERT INTO ut.activity_collect (uid, activity_id, create_time, update_time, deleted) VALUES (1367497062361047042, 14, '2021-03-05 00:30:43', '2021-03-05 00:30:43', 0);