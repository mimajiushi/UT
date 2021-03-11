create table activity_appointment
(
    id          bigint unsigned auto_increment
        primary key,
    uid         bigint unsigned               not null,
    activity_id bigint unsigned               not null,
    update_time datetime                      not null on update CURRENT_TIMESTAMP,
    create_time datetime                      not null,
    deleted     tinyint(2) unsigned default 0 not null
);

create index idx_uid_activityid
    on activity_appointment (uid, activity_id);

INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 3, '2020-05-17 13:33:29', '2020-05-17 13:33:06', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 4, '2020-05-17 13:33:29', '2020-05-17 13:33:06', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 2, '2020-05-30 23:52:29', '2020-05-31 07:36:45', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 2, '2020-05-30 23:52:40', '2020-05-31 07:51:50', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 2, '2020-05-31 12:12:20', '2020-05-31 20:10:49', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 2, '2020-05-31 12:16:06', '2020-05-31 20:12:03', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 4, '2020-06-01 15:09:03', '2020-05-31 20:28:15', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 3, '2020-05-31 15:19:56', '2020-05-31 20:28:22', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 2, '2020-06-01 15:09:41', '2020-05-31 22:40:34', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 2, '2020-05-31 17:38:27', '2020-06-01 01:37:40', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 2, '2020-05-31 17:38:31', '2020-06-01 01:37:43', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 2, '2020-05-31 17:38:31', '2020-06-01 01:37:43', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 2, '2020-05-31 17:38:32', '2020-06-01 01:37:48', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 4, '2020-06-05 18:33:02', '2020-06-02 01:33:42', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (214, 4, '2020-06-05 13:45:35', '2020-06-05 13:45:35', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (214, 10, '2020-06-05 13:46:45', '2020-06-05 13:46:45', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (214, 8, '2020-06-05 15:16:05', '2020-06-05 15:16:05', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 10, '2020-06-05 18:14:31', '2020-06-06 02:13:42', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 13, '2020-06-05 20:54:34', '2020-06-05 20:54:30', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 13, '2020-06-09 16:07:39', '2020-06-05 20:54:36', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (214, 13, '2020-06-06 16:43:18', '2020-06-06 16:39:11', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (214, 13, '2020-06-06 16:43:21', '2020-06-06 16:43:21', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 13, '2020-06-06 21:22:25', '2020-06-06 21:22:25', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 13, '2020-06-09 16:07:40', '2020-06-09 16:07:41', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 14, '2020-06-28 10:58:43', '2020-06-09 16:15:59', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (213, 14, '2020-06-17 10:22:19', '2020-06-17 10:22:19', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 14, '2020-06-30 20:02:14', '2020-06-30 20:02:13', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (21, 14, '2020-06-30 20:02:16', '2020-06-30 20:02:17', 0);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (219, 14, '2020-07-07 14:20:06', '2020-07-07 14:20:00', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (219, 14, '2020-07-07 14:20:08', '2020-07-07 14:20:08', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (1367497062361047042, 14, '2021-03-05 00:11:11', '2021-03-05 00:11:06', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (1367497062361047042, 14, '2021-03-05 00:11:12', '2021-03-05 00:11:12', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (1367497062361047042, 14, '2021-03-05 00:30:40', '2021-03-05 00:11:16', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (1367497062361047042, 14, '2021-03-05 00:30:44', '2021-03-05 00:30:42', 1);
INSERT INTO ut.activity_appointment (uid, activity_id, update_time, create_time, deleted) VALUES (1367497062361047042, 14, '2021-03-05 00:30:45', '2021-03-05 00:30:46', 0);