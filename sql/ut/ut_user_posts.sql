create table user_posts
(
    id          bigint unsigned auto_increment
        primary key,
    uid         bigint unsigned not null,
    post_id     bigint unsigned not null,
    create_time datetime        not null on update CURRENT_TIMESTAMP,
    update_time datetime        not null on update CURRENT_TIMESTAMP,
    deleted     tinyint(2)      not null
)
    charset = utf8mb4;

create index idx_uid_postid
    on user_posts (uid, post_id);

INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (21, 2, '2020-05-23 16:05:35', '2020-05-23 16:05:35', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (21, 3, '2020-05-25 18:51:06', '2020-05-25 18:51:06', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (21, 4, '2020-05-28 22:01:32', '2020-05-28 22:01:32', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (21, 5, '2020-05-28 21:55:37', '2020-05-28 21:55:37', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (22, 3, '2020-05-12 13:22:50', '2020-05-14 13:23:20', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (23, 3, '2020-05-14 13:24:00', '2020-05-14 13:24:00', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (22, 6, '2020-05-12 13:22:50', '2020-05-14 13:23:20', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (21, 2, '2020-05-23 16:05:35', '2020-05-23 16:05:35', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (21, 2, '2020-05-23 16:05:35', '2020-05-23 16:05:35', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (21, 2, '2020-05-23 16:05:35', '2020-05-23 16:05:35', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (21, 2, '2020-05-23 16:05:35', '2020-05-23 16:05:35', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (21, 2, '2020-05-25 18:48:40', '2020-05-25 18:48:40', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 18, '2020-05-23 16:12:40', '2020-05-23 16:12:40', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 18, '2020-05-23 16:12:50', '2020-05-23 16:12:50', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 18, '2020-05-23 16:54:06', '2020-05-23 16:54:06', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 18, '2020-05-23 16:58:48', '2020-05-23 16:58:48', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 18, '2020-05-23 17:03:50', '2020-05-23 17:03:50', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 18, '2020-05-23 17:04:42', '2020-05-23 17:04:42', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 18, '2020-05-26 20:44:50', '2020-05-26 20:44:50', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 24, '2020-05-24 02:55:21', '2020-05-23 18:55:59', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 26, '2020-05-28 22:12:13', '2020-05-28 22:12:13', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 30, '2020-05-27 04:42:58', '2020-05-26 20:43:38', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 18, '2020-05-31 19:24:15', '2020-05-31 19:24:15', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (21, 33, '2020-06-09 16:00:03', '2020-06-09 16:00:03', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 41, '2020-06-06 01:24:39', '2020-06-05 17:25:28', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (214, 41, '2020-06-06 16:19:09', '2020-06-06 16:19:09', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (214, 35, '2020-06-06 16:43:56', '2020-06-06 16:43:55', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (213, 33, '2020-06-21 13:47:36', '2020-06-21 13:47:36', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (214, 42, '2020-06-30 21:15:10', '2020-06-30 21:15:10', 1);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (214, 42, '2020-06-30 21:15:14', '2020-06-30 21:15:13', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (214, 39, '2020-06-30 21:17:46', '2020-06-30 21:17:45', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (214, 43, '2020-06-30 21:17:51', '2020-06-30 21:17:51', 0);
INSERT INTO ut.user_posts (uid, post_id, create_time, update_time, deleted) VALUES (214, 40, '2020-06-30 21:18:02', '2020-06-30 21:18:01', 0);