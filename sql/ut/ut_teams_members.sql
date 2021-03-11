create table teams_members
(
    id          bigint unsigned auto_increment
        primary key,
    team_id     bigint     not null,
    uid         bigint     not null,
    is_leader   tinyint(2) not null comment '0-队员 1-队长',
    deleted     tinyint(2) not null,
    create_time datetime   not null,
    update_time datetime   not null on update CURRENT_TIMESTAMP
)
    charset = utf8mb4;

create index idx_team_id
    on teams_members (team_id);

create index idx_uid
    on teams_members (uid);

INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (1, 1, 21, 1, 1, '2020-03-14 21:16:50', '2020-06-28 12:50:08');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (2, 21, 21, 0, 0, '2020-03-15 12:57:57', '2020-03-14 01:57:37');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (3, 22, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (4, 2, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (5, 3, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (6, 4, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (7, 5, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (8, 6, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (9, 7, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (10, 8, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (11, 9, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (12, 10, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (13, 11, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (14, 12, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (15, 13, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (16, 14, 26, 1, 0, '2020-03-15 13:01:02', '2020-03-25 10:57:31');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (17, 15, 27, 1, 0, '2020-03-15 13:01:02', '2020-03-25 10:57:34');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (18, 16, 28, 1, 0, '2020-03-15 13:01:02', '2020-03-25 10:57:38');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (19, 17, 29, 1, 0, '2020-03-15 13:01:02', '2020-03-25 10:57:41');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (20, 18, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (21, 19, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (22, 20, 21, 1, 0, '2020-03-15 13:01:02', '2020-03-14 02:00:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (23, 1, 22, 0, 1, '2020-03-15 12:57:57', '2020-06-28 12:50:08');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (24, 1, 23, 0, 1, '2020-03-15 12:57:57', '2020-06-28 12:50:08');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (25, 1, 24, 0, 1, '2020-03-15 12:57:57', '2020-06-28 12:50:08');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (26, 1, 25, 0, 1, '2020-03-15 12:57:57', '2020-06-28 12:50:08');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (27, 23, 21, 1, 0, '2020-03-15 23:07:44', '2020-03-14 12:58:13');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (31, 2, 29, 0, 0, '2020-03-26 21:18:39', '2020-03-26 00:32:19');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (32, 2, 28, 0, 0, '2020-03-26 21:20:13', '2020-03-26 12:25:49');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (33, 2, 27, 0, 0, '2020-03-26 21:21:39', '2020-03-26 12:25:52');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (34, 16, 21, 0, 0, '2020-03-26 23:25:39', '2020-03-26 02:49:54');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (35, 2, 25, 0, 0, '2020-03-26 23:28:14', '2020-03-26 12:26:25');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (36, 16, 21, 0, 0, '2020-03-26 23:28:29', '2020-05-21 14:17:30');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (37, 16, 21, 0, 0, '2020-03-26 23:30:01', '2020-05-21 14:51:24');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (38, 16, 21, 0, 1, '2020-03-26 23:31:25', '2020-05-21 14:51:09');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (39, 16, 21, 0, 1, '2020-03-26 23:32:45', '2020-05-21 14:51:07');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (40, 24, 30, 1, 0, '2020-03-26 23:32:45', '2020-05-06 16:01:46');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (41, 25, 21, 1, 0, '2020-05-18 21:45:36', '2020-05-21 14:50:23');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (42, 26, 21, 1, 0, '2020-05-19 18:18:29', '2020-05-19 18:18:29');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (43, 27, 213, 0, 0, '2020-05-20 05:07:49', '2020-05-21 14:17:25');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (44, 28, 213, 1, 1, '2020-05-20 05:21:47', '2020-05-21 17:20:14');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (45, 29, 213, 1, 0, '2020-05-20 05:39:42', '2020-05-21 14:17:22');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (46, 30, 213, 0, 1, '2020-05-21 03:07:13', '2020-06-07 20:49:52');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (47, 31, 213, 1, 1, '2020-05-21 22:07:51', '2020-05-21 16:54:08');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (48, 32, 213, 0, 0, '2020-05-21 22:08:37', '2020-05-21 14:17:19');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (49, 1, 30, 0, 1, '2020-03-15 13:01:02', '2020-05-21 14:53:47');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (50, 33, 21, 1, 1, '2020-03-15 13:01:02', '2020-05-21 16:39:39');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (51, 33, 22, 0, 1, '2020-03-15 13:01:02', '2020-05-21 16:39:39');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (52, 34, 213, 1, 1, '2020-05-22 07:04:09', '2020-05-21 23:05:05');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (53, 35, 213, 0, 0, '2020-05-24 02:10:27', '2020-05-23 18:11:05');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (59, 29, 21, 0, 0, '2020-06-06 00:30:51', '2020-06-05 16:31:40');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (60, 30, 21, 1, 0, '2020-06-06 00:30:57', '2020-06-05 16:31:46');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (61, 32, 21, 1, 0, '2020-06-06 00:31:06', '2020-06-05 16:31:55');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (62, 35, 21, 1, 0, '2020-06-06 00:33:08', '2020-06-05 16:33:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (63, 29, 214, 0, 1, '2020-06-06 00:45:18', '2020-06-06 22:11:14');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (64, 5, 213, 0, 1, '2020-06-05 21:10:48', '2020-06-20 13:55:25');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (65, 11, 213, 0, 1, '2020-06-05 21:10:56', '2020-06-06 16:27:54');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (66, 19, 214, 0, 0, '2020-06-06 16:45:33', '2020-06-06 16:45:33');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (67, 1, 214, 0, 1, '2020-06-06 16:45:52', '2020-06-28 12:50:08');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (68, 29, 214, 0, 1, '2020-06-06 16:51:16', '2020-06-28 14:04:40');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (69, 35, 214, 0, 0, '2020-06-06 21:12:35', '2020-06-06 21:12:35');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (70, 27, 214, 1, 0, '2020-06-07 16:03:16', '2020-06-07 16:03:16');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (71, 36, 213, 1, 0, '2020-06-07 20:37:59', '2020-06-07 20:37:59');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (72, 37, 21, 0, 1, '2020-06-09 15:38:45', '2021-03-05 00:34:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (73, 38, 213, 1, 1, '2020-06-09 17:22:56', '2020-06-09 17:23:23');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (74, 36, 21, 0, 1, '2020-06-10 16:52:05', '2020-06-28 14:05:24');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (75, 27, 21, 0, 0, '2020-06-10 18:16:41', '2020-06-10 18:16:40');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (76, 24, 21, 0, 0, '2020-06-10 18:16:54', '2020-06-10 18:16:53');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (77, 17, 21, 0, 0, '2020-06-10 18:18:19', '2020-06-10 18:18:18');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (78, 21, 218, 0, 0, '2020-07-01 17:19:25', '2020-07-01 17:19:25');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (1367144987143905282, 1367144987106156545, 21, 0, 1, '2021-03-04 00:09:00', '2021-03-05 00:16:07');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (1367508830919499778, 1367144987106156545, 1367497062361047042, 1, 1, '2021-03-05 00:14:47', '2021-03-05 00:16:07');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (1367513760216555522, 37, 1367497062361047042, 1, 1, '2021-03-05 00:34:22', '2021-03-05 00:34:57');
INSERT INTO ut.teams_members (id, team_id, uid, is_leader, deleted, create_time, update_time) VALUES (1368005818899480578, 21, 1367497062361047042, 1, 0, '2021-03-06 09:09:38', '2021-03-06 09:09:38');