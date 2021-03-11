create table teams_recruitments
(
    id          bigint unsigned auto_increment
        primary key,
    team_id     bigint unsigned         not null,
    name        varchar(50)             not null comment '招聘岗位名称',
    tag_ids     varchar(255) default '' not null,
    description text                    not null comment '岗位要求、描述',
    deleted     tinyint(2)   default 0  not null,
    create_time datetime                not null,
    update_time datetime                not null on update CURRENT_TIMESTAMP
)
    charset = utf8mb4;

create index idx_team_id
    on teams_recruitments (team_id);

INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (1, 1, 'Java后端开发999批', '1,2,3', 'wfawfawdawdawdawdawdawdawdawd', 0, '2020-03-15 16:42:27', '2020-05-20 22:12:54');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (2, 1, 'Java后端开发2批', '', '要求：Java基础扎实，能熟练运用现有的流行框架', 1, '2020-03-15 16:43:14', '2020-06-07 21:54:40');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (3, 1, 'Java后端开发3批', '', '要求：Java基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:29', '2020-03-14 06:29:02');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (4, 1, 'Java后端开发4批', '', '要求：Java基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:32', '2020-03-14 06:29:05');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (5, 1, 'Java后端开发5批', '', '要求：Java基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:36', '2020-03-14 06:29:09');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (6, 2, 'php后端开发1批', '1,2,3', '要求：php基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 16:42:27', '2020-03-15 18:21:06');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (7, 2, 'php后端开发2批', '', '要求：php基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 16:43:14', '2020-03-15 17:08:25');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (8, 2, 'php后端开发3批', '', '要求：php基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:29', '2020-03-14 06:29:02');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (9, 2, 'php后端开发4批', '', '要求：php基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:32', '2020-03-14 06:29:05');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (10, 2, 'php后端开发5批', '', '要求：php基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:36', '2020-03-14 06:29:09');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (11, 3, 'go后端开发1批', '1,2,3', '要求：go基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 16:42:27', '2020-03-15 04:28:00');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (12, 3, 'go后端开发2批', '', '要求：go基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 16:43:14', '2020-03-15 04:28:01');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (13, 3, 'go后端开发3批', '', '要求：go基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:29', '2020-03-15 04:28:03');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (14, 3, 'go后端开发4批', '', '要求：go基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:32', '2020-03-15 04:28:04');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (15, 3, 'go后端开发5批', '', '要求：go基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:36', '2020-03-15 04:28:06');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (16, 4, 'c++后端开发1批', '1,2,3', '要求：c++基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 16:42:27', '2020-03-15 04:28:14');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (17, 4, 'c++后端开发2批', '', '要求：c++基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 16:43:14', '2020-03-15 04:28:16');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (18, 4, 'c++后端开发3批', '', '要求：c++基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:29', '2020-03-15 04:28:17');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (19, 4, 'c++后端开发4批', '', '要求：c++基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:32', '2020-03-15 04:28:20');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (20, 4, 'c++后端开发5批', '', '要求：c++基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:36', '2020-03-15 04:28:22');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (21, 5, 'nodejs后端开发1批', '1,2,3', '要求：nodejs基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 16:42:27', '2020-03-15 04:28:33');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (22, 5, 'nodejs后端开发2批', '', '要求：nodejs基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 16:43:14', '2020-03-15 04:28:35');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (23, 5, 'nodejs后端开发3批', '', '要求：nodejs基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:29', '2020-03-15 04:28:36');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (24, 5, 'nodejs后端开发4批', '', '要求：nodejs基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:32', '2020-03-15 04:28:38');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (25, 5, 'nodejs后端开发5批', '', '要求：nodejs基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:36', '2020-03-15 04:28:40');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (26, 14, 'nodejs后端开发6批', '', '要求：nodejs基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:36', '2020-03-15 04:28:40');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (27, 15, 'nodejs后端开发10批', '', '要求：nodejs基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:36', '2020-03-15 04:28:40');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (28, 16, 'nodejs后端开发11批', '', '要求：nodejs基础扎实，能熟练运用现有的流行框架', 0, '2020-03-15 17:08:36', '2020-03-15 04:28:40');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (29, 1, 'Java后端开发99批', '', 'wfawfawdawdawdawdawdawdawdawd', 0, '2020-05-20 22:08:20', '2020-05-20 22:08:20');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (30, 27, 'Java开发', '', '德国人风格特瑞特让他给', 0, '2020-05-21 06:58:27', '2020-05-21 07:12:20');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (31, 30, 'PHP开发', '', '的分数法大师傅', 0, '2020-05-21 06:59:02', '2020-05-20 22:59:37');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (32, 27, '前端开发', '', '的开发设计覅哦都没发', 0, '2020-05-21 07:05:59', '2020-05-20 23:06:34');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (33, 29, 'cv工程师', '', '大师傅似的繁忙发的', 0, '2020-05-21 07:44:25', '2020-05-20 23:45:01');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (34, 32, '前端开发', '', 'awdawdadawdawdawdaw', 1, '2020-05-22 19:11:46', '2020-06-09 15:35:11');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (35, 35, '打碟', '', '的范德萨发达省份', 0, '2020-05-24 02:10:45', '2020-05-23 18:11:23');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (36, 36, 'Java开发', '', '地方的范德萨发达省份', 1, '2020-06-07 20:38:41', '2020-06-07 22:22:04');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (37, 36, 'asd', '', 'asd', 1, '2020-06-07 22:24:19', '2020-06-07 22:26:18');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (38, 36, 'asd', '', 'asd', 1, '2020-06-07 22:24:22', '2020-06-07 22:26:21');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (39, 36, 'asd', '', 'asd', 1, '2020-06-07 22:26:36', '2020-06-07 22:26:40');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (40, 36, 'asd', '', 'asd', 1, '2020-06-07 22:26:38', '2020-06-07 22:26:49');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (41, 36, 'asd', '', 'asd', 1, '2020-06-07 22:27:17', '2020-06-07 22:34:01');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (42, 36, 'dsf', '', 'sdfdf', 1, '2020-06-07 22:34:06', '2020-06-07 22:37:24');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (43, 36, 'ewre', '', 'wewe', 1, '2020-06-07 22:35:28', '2020-06-07 22:35:31');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (44, 36, '前端开发', '', '请问破后给你心', 0, '2020-06-08 20:20:39', '2020-06-08 20:20:38');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (45, 21, 'Java开发', '', '负责xxx产品的开发与维护', 0, '2020-06-09 15:36:08', '2020-06-09 15:36:08');
INSERT INTO ut.teams_recruitments (id, team_id, name, tag_ids, description, deleted, create_time, update_time) VALUES (46, 21, '前端开发', '', '负责xxx产品前端开发，要求熟悉Vue等框架', 0, '2020-06-09 15:48:35', '2020-06-09 15:48:34');