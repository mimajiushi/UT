create table teams_tags
(
    id          bigint unsigned auto_increment
        primary key,
    team_id     bigint unsigned   not null,
    tag_id      int               not null,
    deleted     tinyint default 0 not null,
    create_time datetime          not null,
    update_time datetime          not null on update CURRENT_TIMESTAMP
)
    charset = utf8mb4;

create index idx_team_id
    on teams_tags (team_id);

create index tag_id
    on teams_tags (tag_id);

INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (1, 1, 1, 1, '2020-03-14 21:17:18', '2020-03-13 22:15:26');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (2, 1, 2, 1, '2020-03-14 21:17:18', '2020-03-13 22:15:26');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (3, 1, 1, 1, '2020-03-14 21:18:45', '2020-03-13 22:15:30');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (4, 1, 1, 1, '2020-03-14 21:18:48', '2020-03-13 22:15:36');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (5, 1, 2, 1, '2020-03-14 21:18:48', '2020-03-13 22:15:36');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (6, 1, 1, 1, '2020-03-14 21:18:54', '2020-03-14 02:58:23');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (7, 1, 2, 1, '2020-03-14 21:18:54', '2020-03-14 02:58:23');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (8, 1, 3, 1, '2020-03-14 21:18:54', '2020-03-14 02:58:23');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (9, 1, 1, 1, '2020-03-15 13:54:02', '2020-03-14 07:40:26');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (10, 1, 2, 1, '2020-03-15 13:54:02', '2020-03-14 07:40:26');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (11, 2, 2, 0, '2020-03-15 14:18:51', '2020-03-14 03:25:31');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (12, 2, 3, 0, '2020-03-15 14:19:01', '2020-03-14 03:25:34');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (13, 1, 1, 1, '2020-03-15 18:15:43', '2020-03-14 07:42:07');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (14, 1, 2, 1, '2020-03-15 18:15:43', '2020-03-14 07:42:07');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (15, 1, 3, 1, '2020-03-15 18:15:43', '2020-03-14 07:42:07');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (16, 1, 1, 0, '2020-03-15 18:15:57', '2020-03-14 07:42:07');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (17, 1, 2, 0, '2020-03-15 18:15:57', '2020-03-14 07:42:07');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (18, 25, 1, 0, '2020-05-18 21:45:36', '2020-05-18 21:45:36');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (19, 25, 2, 0, '2020-05-18 21:45:36', '2020-05-18 21:45:36');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (20, 25, 3, 0, '2020-05-18 21:45:36', '2020-05-18 21:45:36');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (21, 26, 1, 0, '2020-05-19 18:18:29', '2020-05-19 18:18:29');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (22, 26, 2, 0, '2020-05-19 18:18:29', '2020-05-19 18:18:29');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (23, 26, 3, 0, '2020-05-19 18:18:29', '2020-05-19 18:18:29');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (24, 27, 2, 1, '2020-05-20 05:07:50', '2020-05-20 17:30:37');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (25, 27, 3, 1, '2020-05-20 05:07:50', '2020-05-20 17:30:37');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (26, 27, 5, 1, '2020-05-20 05:07:50', '2020-05-20 17:30:37');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (27, 28, 5, 0, '2020-05-20 05:21:47', '2020-05-19 21:22:21');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (28, 28, 8, 0, '2020-05-20 05:21:47', '2020-05-19 21:22:21');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (29, 28, 15, 0, '2020-05-20 05:21:47', '2020-05-19 21:22:21');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (30, 29, 13, 0, '2020-05-20 05:39:43', '2020-05-19 21:40:17');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (31, 29, 14, 0, '2020-05-20 05:39:43', '2020-05-19 21:40:17');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (32, 29, 16, 0, '2020-05-20 05:39:43', '2020-05-19 21:40:17');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (33, 27, 2, 1, '2020-05-21 01:30:02', '2020-05-20 17:30:59');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (34, 27, 3, 1, '2020-05-21 01:30:02', '2020-05-20 17:30:59');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (35, 27, 2, 1, '2020-05-21 01:30:24', '2020-05-20 17:34:01');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (36, 27, 3, 1, '2020-05-21 01:30:24', '2020-05-20 17:34:01');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (37, 27, 4, 1, '2020-05-21 01:30:24', '2020-05-20 17:34:01');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (38, 27, 2, 1, '2020-05-21 01:33:27', '2020-05-20 17:34:07');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (39, 27, 3, 1, '2020-05-21 01:33:27', '2020-05-20 17:34:07');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (40, 27, 8, 1, '2020-05-21 01:33:27', '2020-05-20 17:34:07');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (41, 27, 2, 1, '2020-05-21 01:33:32', '2020-05-20 18:55:35');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (42, 27, 3, 1, '2020-05-21 01:33:32', '2020-05-20 18:55:35');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (43, 27, 4, 1, '2020-05-21 01:33:32', '2020-05-20 18:55:35');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (44, 27, 2, 1, '2020-05-21 02:55:00', '2020-05-21 10:17:46');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (45, 27, 3, 1, '2020-05-21 02:55:00', '2020-05-21 10:17:46');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (46, 30, 2, 1, '2020-05-21 03:07:13', '2020-05-23 18:11:46');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (47, 30, 5, 1, '2020-05-21 03:07:13', '2020-05-23 18:11:46');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (48, 30, 8, 1, '2020-05-21 03:07:13', '2020-05-23 18:11:46');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (49, 27, 2, 0, '2020-05-21 18:17:11', '2020-05-21 10:17:47');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (50, 27, 3, 0, '2020-05-21 18:17:11', '2020-05-21 10:17:47');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (51, 27, 4, 0, '2020-05-21 18:17:11', '2020-05-21 10:17:47');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (52, 31, 3, 0, '2020-05-21 22:07:51', '2020-05-21 14:08:27');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (53, 31, 6, 0, '2020-05-21 22:07:51', '2020-05-21 14:08:27');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (54, 31, 8, 0, '2020-05-21 22:07:51', '2020-05-21 14:08:27');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (55, 32, 2, 1, '2020-05-21 22:08:37', '2020-05-21 15:46:43');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (56, 32, 5, 1, '2020-05-21 22:08:37', '2020-05-21 15:46:43');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (57, 32, 8, 1, '2020-05-21 22:08:37', '2020-05-21 15:46:43');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (58, 32, 2, 1, '2020-05-21 23:46:07', '2020-05-21 15:46:50');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (59, 32, 3, 1, '2020-05-21 23:46:07', '2020-05-21 15:46:50');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (60, 32, 5, 1, '2020-05-21 23:46:07', '2020-05-21 15:46:50');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (61, 32, 2, 1, '2020-05-21 23:46:14', '2020-05-22 11:12:02');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (62, 32, 3, 1, '2020-05-21 23:46:14', '2020-05-22 11:12:02');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (63, 32, 6, 1, '2020-05-21 23:46:14', '2020-05-22 11:12:02');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (64, 34, 1, 0, '2020-05-22 07:04:09', '2020-05-21 23:04:45');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (65, 34, 2, 0, '2020-05-22 07:04:09', '2020-05-21 23:04:45');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (66, 34, 5, 0, '2020-05-22 07:04:09', '2020-05-21 23:04:45');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (67, 32, 2, 0, '2020-05-22 19:11:25', '2020-05-22 11:12:02');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (68, 32, 3, 0, '2020-05-22 19:11:25', '2020-05-22 11:12:02');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (69, 32, 33, 0, '2020-05-22 19:11:25', '2020-05-22 11:12:02');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (70, 35, 3, 0, '2020-05-24 02:10:27', '2020-05-23 18:11:05');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (71, 35, 5, 0, '2020-05-24 02:10:27', '2020-05-23 18:11:05');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (72, 35, 8, 0, '2020-05-24 02:10:27', '2020-05-23 18:11:05');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (73, 30, 2, 0, '2020-05-24 02:11:08', '2020-05-23 18:11:46');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (74, 30, 3, 0, '2020-05-24 02:11:08', '2020-05-23 18:11:46');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (75, 30, 5, 0, '2020-05-24 02:11:08', '2020-05-23 18:11:46');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (76, 36, 2, 0, '2020-06-07 20:37:59', '2020-06-07 20:37:59');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (77, 36, 5, 0, '2020-06-07 20:37:59', '2020-06-07 20:37:59');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (78, 36, 8, 0, '2020-06-07 20:37:59', '2020-06-07 20:37:59');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (79, 37, 1, 0, '2020-06-09 15:38:45', '2020-06-09 15:38:45');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (80, 37, 2, 0, '2020-06-09 15:38:45', '2020-06-09 15:38:45');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (81, 37, 3, 0, '2020-06-09 15:38:45', '2020-06-09 15:38:45');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (82, 38, 1, 0, '2020-06-09 17:22:56', '2020-06-09 17:22:56');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (83, 38, 2, 0, '2020-06-09 17:22:56', '2020-06-09 17:22:56');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (84, 38, 3, 0, '2020-06-09 17:22:56', '2020-06-09 17:22:56');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (1367144987391369218, 1367144987106156545, 7, 0, '2021-03-04 00:09:00', '2021-03-04 00:08:59');
INSERT INTO ut.teams_tags (id, team_id, tag_id, deleted, create_time, update_time) VALUES (1367144987416535041, 1367144987106156545, 8, 0, '2021-03-04 00:09:00', '2021-03-04 00:08:59');