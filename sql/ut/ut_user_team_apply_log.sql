create table user_team_apply_log
(
    id             bigint unsigned auto_increment
        primary key,
    uid            bigint unsigned        not null,
    team_id        bigint                 not null,
    recruitment_id bigint                 not null comment '0-直接申请加入团队，> 0则是申请具体职位',
    mode           tinyint(2)             not null comment '1-用户申请加入团队 2-团队邀请用户',
    status         tinyint(2)             not null comment '0-待处理 1-申请通过 -1-申请被拒绝',
    message        varchar(50) default '' not null comment '留言，可以是联系方式或拒绝原因',
    deleted        tinyint(2)  default 0  not null,
    create_time    datetime               not null,
    update_time    datetime               not null on update CURRENT_TIMESTAMP
)
    charset = utf8mb4;

create index idx_team_id
    on user_team_apply_log (team_id);

create index idx_uid
    on user_team_apply_log (uid);

INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (30, 1, 1, 1, 0, '', 0, '2020-03-15 08:07:43', '2020-03-27 00:07:18');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (30, 1, 0, 2, -1, '不想加了', 0, '2020-03-26 02:28:28', '2020-03-26 02:28:28');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (30, 2, 9, 1, 0, '', 0, '2020-03-15 08:07:42', '2020-03-15 08:07:42');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (30, 3, 11, 1, 0, '', 0, '2020-03-15 08:01:50', '2020-03-15 08:01:50');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (30, 4, 0, 1, 0, '', 0, '2020-03-15 04:35:27', '2020-03-15 04:35:27');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 1, 0, 2, 1, '微信：123123', 0, '2020-03-26 02:28:40', '2020-03-26 02:28:40');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (22, 1, 1, 2, 0, '', 0, '2020-03-15 08:03:00', '2020-03-15 08:03:00');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (23, 1, 0, 2, 0, '', 0, '2020-03-15 08:03:05', '2020-03-15 08:03:05');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (24, 1, 1, 2, 0, '', 0, '2020-03-15 03:58:33', '2020-03-15 03:58:33');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (25, 1, 0, 2, 0, '', 0, '2020-03-15 03:02:20', '2020-03-15 03:02:20');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (29, 4, 11, 1, 0, '', 0, '2020-03-15 04:34:49', '2020-03-15 04:34:49');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (29, 3, 0, 1, 0, '', 0, '2020-03-15 03:58:35', '2020-03-15 03:58:35');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (29, 2, 1, 1, 0, '', 0, '2020-03-26 00:34:40', '2020-03-26 02:53:58');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (29, 1, 0, 1, 0, '', 0, '2020-03-15 03:58:37', '2020-03-15 03:58:37');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (22, 2, 6, 2, 0, '', 0, '2020-03-15 08:09:09', '2020-03-15 08:09:09');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (23, 2, 0, 2, 0, '', 0, '2020-03-15 08:09:10', '2020-03-15 08:09:10');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (24, 2, 8, 2, 0, '', 0, '2020-03-15 04:35:03', '2020-03-15 04:35:03');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (25, 2, 0, 2, 0, '', 0, '2020-03-15 03:02:20', '2020-03-15 03:02:20');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (26, 2, 7, 2, 0, '', 0, '2020-03-15 04:35:00', '2020-03-15 04:35:00');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (30, 2, 0, 2, 0, '', 0, '2020-03-15 08:05:16', '2020-03-15 08:05:16');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (30, 3, 0, 2, 0, '', 0, '2020-03-15 08:05:14', '2020-03-15 08:05:14');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (30, 2, 2, 2, 0, '', 0, '2020-03-15 13:26:28', '2020-03-15 13:26:28');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (30, 3, 2, 2, 0, '', 0, '2020-03-15 13:26:34', '2020-03-15 13:26:34');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (29, 1, 1, 1, -1, '已经满人了', 0, '2020-03-25 21:45:16', '2020-03-26 18:44:32');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (28, 1, 1, 1, -1, '已经满人了', 0, '2020-03-25 21:45:18', '2020-03-26 18:44:32');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 14, 0, 1, 1, '微信：1498780478', 0, '2020-03-25 11:35:03', '2020-03-25 11:35:03');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 15, 27, 1, -1, '已经满人啦', 0, '2020-03-25 11:35:53', '2020-03-25 11:35:53');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 16, 28, 1, 0, '', 0, '2020-03-25 11:01:12', '2020-03-25 11:01:12');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 16, 0, 2, -1, '233333', 0, '2020-03-26 02:28:40', '2020-03-26 23:32:45');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 17, 0, 2, -1, '的', 0, '2020-03-26 02:28:40', '2020-06-10 18:18:19');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (29, 1, 0, 2, 0, '', 0, '2020-03-29 20:27:59', '2020-03-29 20:26:40');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 24, 0, 2, 1, '微信：123123123', 0, '2020-05-06 16:32:28', '2020-06-10 18:16:54');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (213, 2, 6, 1, 0, '', 0, '2020-05-18 04:26:12', '2020-05-17 20:26:44');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (213, 1, 0, 1, 0, '', 0, '2020-05-18 05:32:53', '2020-05-17 21:33:25');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (213, 24, 0, 1, 0, '', 0, '2020-05-18 22:35:54', '2020-05-18 14:36:27');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (213, 4, 16, 1, 0, '', 0, '2020-05-18 22:40:03', '2020-05-18 14:40:36');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (29, 30, 31, 2, 0, '', 0, '2020-05-21 20:01:43', '2020-05-21 12:02:18');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 27, 30, 2, -1, '满人了', 0, '2020-05-21 20:37:45', '2020-06-10 18:16:41');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (23, 30, 0, 2, 0, '', 0, '2020-05-22 07:06:14', '2020-05-21 23:06:50');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 32, 0, 1, 0, '', 0, '2020-06-05 13:46:03', '2020-06-05 13:46:02');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 29, 0, 1, 1, '', 0, '2020-06-05 13:46:21', '2020-06-06 00:45:17');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (213, 26, 0, 1, 0, '', 0, '2020-06-05 22:23:24', '2020-06-05 14:24:13');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 1, 0, 1, 0, '', 0, '2020-06-05 14:52:22', '2020-06-05 14:52:22');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 1, 1, 2, 1, 'gg', 0, '2020-06-05 14:53:28', '2020-06-06 16:45:52');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 19, 0, 2, 1, '', 0, '2020-06-05 14:56:31', '2020-06-06 16:45:33');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 29, 33, 2, -1, '', 0, '2020-06-05 23:03:33', '2020-06-06 16:51:16');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (213, 3, 0, 1, 0, '', 0, '2020-06-05 23:05:06', '2020-06-05 15:05:55');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 35, 0, 1, 1, 'asdasd', 0, '2020-06-05 15:27:22', '2020-06-06 00:33:08');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 32, 0, 1, 1, 'asdasdasd', 0, '2020-06-05 15:27:27', '2020-06-06 00:31:06');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 30, 0, 1, -1, 'asd', 0, '2020-06-05 15:27:30', '2020-06-06 00:30:57');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 29, 0, 1, 1, 'asd', 0, '2020-06-05 15:27:35', '2020-06-06 00:30:51');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (213, 11, 0, 1, -1, '满人了', 0, '2020-06-06 00:51:33', '2020-06-05 21:10:56');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (213, 5, 25, 1, 1, '微信：xxxxx', 0, '2020-06-06 01:03:25', '2020-06-05 21:10:48');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 30, 0, 1, 0, '', 0, '2020-06-06 16:34:46', '2020-06-06 16:34:46');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 35, 0, 1, 1, '完全ok', 0, '2020-06-06 16:38:31', '2020-06-06 21:12:35');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 2, 10, 1, 0, '', 0, '2020-06-06 16:38:52', '2020-06-06 16:38:51');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 3, 0, 1, 0, '', 0, '2020-06-06 16:40:44', '2020-06-06 16:40:44');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 27, 0, 1, 1, '', 0, '2020-06-06 16:41:58', '2020-06-07 16:03:16');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (214, 36, 36, 2, 0, '', 0, '2020-06-07 20:39:38', '2020-06-07 20:39:38');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (21, 36, 36, 2, -1, '满人了', 0, '2020-06-07 20:47:31', '2020-06-10 16:52:05');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (27, 36, 36, 2, 0, '', 0, '2020-06-07 20:48:16', '2020-06-07 20:48:15');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (215, 1, 1, 2, 0, '', 0, '2020-06-10 18:36:57', '2020-06-10 18:36:57');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (215, 2, 7, 2, 0, '', 0, '2020-06-10 18:37:01', '2020-06-10 18:37:01');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (215, 3, 12, 2, 0, '', 0, '2020-06-10 18:37:06', '2020-06-10 18:37:05');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (215, 21, 46, 2, 0, '', 0, '2020-06-10 18:37:10', '2020-06-10 18:37:09');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (215, 22, 0, 2, 0, '', 0, '2020-06-10 18:37:14', '2020-06-10 18:37:14');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (218, 21, 0, 2, 1, '很高兴加入', 0, '2020-06-30 20:01:28', '2020-07-01 17:19:25');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (1367497062361047042, 1367144987106156545, 0, 1, 1, '1498791212', 0, '2021-03-05 00:14:27', '2021-03-05 00:14:47');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (1367497062361047042, 37, 0, 1, 1, '你好', 0, '2021-03-05 00:34:10', '2021-03-05 00:34:22');
INSERT INTO ut.user_team_apply_log (uid, team_id, recruitment_id, mode, status, message, deleted, create_time, update_time) VALUES (1367497062361047042, 21, 45, 2, 1, 'awdaw', 0, '2021-03-06 09:09:22', '2021-03-06 09:09:38');