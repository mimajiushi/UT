create table user_tags
(
    id          bigint unsigned auto_increment
        primary key,
    uid         bigint unsigned      not null,
    tag_id      int(11) unsigned     not null,
    deleted     tinyint(2) default 0 not null,
    create_time datetime             not null,
    update_time datetime             not null
)
    charset = utf8mb4;

create index tag_id
    on user_tags (tag_id);

create index uid
    on user_tags (uid);

INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (15, 22, 1, 0, '2020-03-12 19:56:32', '2020-03-12 19:56:36');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (16, 22, 2, 0, '2020-03-12 22:32:18', '2020-03-12 19:56:32');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (17, 23, 2, 0, '2020-03-12 22:32:18', '2020-03-12 19:56:32');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (18, 23, 3, 0, '2020-03-12 22:51:44', '2020-03-12 22:51:52');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (76, 21, 1, 1, '2020-03-23 13:24:22', '2020-03-17 08:24:53');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (77, 21, 2, 1, '2020-03-23 13:24:22', '2020-03-17 08:24:53');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (78, 21, 3, 1, '2020-03-23 13:24:22', '2020-03-17 08:24:53');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (79, 25, 4, 0, '2020-03-23 13:24:22', '2020-03-17 08:24:53');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (80, 25, 5, 0, '2020-03-23 13:24:22', '2020-03-17 08:24:53');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (81, 25, 6, 0, '2020-03-23 13:24:22', '2020-03-17 08:24:53');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (82, 30, 7, 0, '2020-03-23 13:24:22', '2020-03-17 08:24:53');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (83, 21, 1, 1, '2020-04-04 23:20:46', '2020-04-04 23:20:46');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (84, 21, 3, 1, '2020-04-04 23:20:46', '2020-04-04 23:20:46');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (85, 21, 4, 1, '2020-04-04 23:20:46', '2020-04-04 23:20:46');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (86, 21, 1, 1, '2020-04-04 23:20:50', '2020-04-04 23:20:50');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (87, 21, 2, 1, '2020-04-04 23:20:50', '2020-04-04 23:20:50');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (88, 21, 3, 1, '2020-04-04 23:20:50', '2020-04-04 23:20:50');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (89, 213, 2, 1, '2020-05-16 21:56:59', '2020-05-16 13:57:30');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (90, 213, 3, 1, '2020-05-16 21:56:59', '2020-05-16 13:57:30');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (91, 213, 1, 1, '2020-05-16 21:57:18', '2020-05-16 13:57:49');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (92, 213, 2, 1, '2020-05-16 21:57:18', '2020-05-16 13:57:49');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (93, 213, 1, 1, '2020-05-17 01:23:40', '2020-05-16 17:24:12');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (94, 213, 2, 1, '2020-05-17 01:23:40', '2020-05-16 17:24:12');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (95, 213, 5, 1, '2020-05-17 01:23:40', '2020-05-16 17:24:12');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (96, 213, 4, 1, '2020-05-17 01:26:08', '2020-05-16 17:26:39');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (97, 213, 5, 1, '2020-05-17 01:26:08', '2020-05-16 17:26:39');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (98, 213, 18, 1, '2020-05-17 01:26:08', '2020-05-16 17:26:39');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (99, 213, 4, 1, '2020-05-17 06:49:36', '2020-05-16 22:50:07');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (100, 213, 18, 1, '2020-05-17 06:49:36', '2020-05-16 22:50:07');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (101, 213, 4, 1, '2020-05-18 19:28:50', '2020-05-18 11:29:23');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (102, 213, 14, 1, '2020-05-18 19:28:50', '2020-05-18 11:29:23');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (103, 213, 18, 1, '2020-05-18 19:28:50', '2020-05-18 11:29:23');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (104, 213, 4, 1, '2020-05-18 22:29:44', '2020-05-18 14:30:17');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (105, 213, 14, 1, '2020-05-18 22:29:44', '2020-05-18 14:30:17');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (106, 213, 4, 1, '2020-05-19 05:22:02', '2020-05-18 21:22:36');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (107, 213, 4, 1, '2020-05-20 23:02:04', '2020-05-20 15:02:39');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (108, 213, 6, 1, '2020-05-20 23:02:04', '2020-05-20 15:02:39');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (109, 213, 4, 1, '2020-06-06 16:29:20', '2020-06-06 16:29:19');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (110, 213, 6, 1, '2020-06-06 16:29:20', '2020-06-06 16:29:19');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (111, 213, 15, 1, '2020-06-06 16:29:20', '2020-06-06 16:29:19');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (112, 213, 4, 1, '2020-06-08 21:35:42', '2020-06-08 21:35:42');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (113, 213, 15, 1, '2020-06-08 21:35:42', '2020-06-08 21:35:42');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (114, 213, 4, 1, '2020-06-08 21:36:38', '2020-06-08 21:36:38');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (115, 213, 8, 1, '2020-06-08 21:36:38', '2020-06-08 21:36:38');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (116, 213, 15, 1, '2020-06-08 21:36:38', '2020-06-08 21:36:38');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (117, 213, 4, 0, '2020-06-13 17:35:57', '2020-06-13 17:35:57');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (118, 213, 8, 0, '2020-06-13 17:35:57', '2020-06-13 17:35:57');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (119, 213, 16, 0, '2020-06-13 17:35:57', '2020-06-13 17:35:57');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (120, 21, 1, 1, '2020-06-27 20:45:20', '2020-06-27 20:45:20');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (121, 21, 3, 1, '2020-06-27 20:45:20', '2020-06-27 20:45:20');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (122, 21, 6, 1, '2020-06-27 20:45:20', '2020-06-27 20:45:20');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (123, 21, 1, 0, '2020-06-27 20:45:24', '2020-06-27 20:45:24');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (124, 21, 2, 0, '2020-06-27 20:45:24', '2020-06-27 20:45:24');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (125, 21, 3, 0, '2020-06-27 20:45:24', '2020-06-27 20:45:24');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (1367507317086134274, 1367497062361047042, 2, 1, '2021-03-05 00:08:46', '2021-03-05 00:08:46');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (1367507317086134275, 1367497062361047042, 3, 1, '2021-03-05 00:08:46', '2021-03-05 00:08:46');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (1367512326527950849, 1367497062361047042, 1, 1, '2021-03-05 00:28:40', '2021-03-05 00:28:40');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (1367512326527950850, 1367497062361047042, 2, 1, '2021-03-05 00:28:40', '2021-03-05 00:28:40');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (1367512326527950851, 1367497062361047042, 3, 1, '2021-03-05 00:28:40', '2021-03-05 00:28:40');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (1368004295444381697, 1367497062361047042, 1, 0, '2021-03-06 09:03:35', '2021-03-06 09:03:34');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (1368004295473741826, 1367497062361047042, 3, 0, '2021-03-06 09:03:35', '2021-03-06 09:03:35');
INSERT INTO ut.user_tags (id, uid, tag_id, deleted, create_time, update_time) VALUES (1368004295473741827, 1367497062361047042, 5, 0, '2021-03-06 09:03:35', '2021-03-06 09:03:35');