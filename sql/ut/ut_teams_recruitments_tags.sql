create table teams_recruitments_tags
(
    id                  bigint unsigned auto_increment
        primary key,
    team_recruitment_id bigint     not null,
    tag_id              int        not null,
    deleted             tinyint(2) not null,
    create_time         datetime   not null,
    update_time         datetime   not null on update CURRENT_TIMESTAMP
)
    comment '此表打算废弃了' charset = utf8mb4;

INSERT INTO ut.teams_recruitments_tags (id, team_recruitment_id, tag_id, deleted, create_time, update_time) VALUES (1, 1, 1, 1, '2020-03-15 18:21:06', '2020-03-15 13:43:23');
INSERT INTO ut.teams_recruitments_tags (id, team_recruitment_id, tag_id, deleted, create_time, update_time) VALUES (2, 1, 2, 1, '2020-03-15 18:21:06', '2020-03-15 13:43:23');
INSERT INTO ut.teams_recruitments_tags (id, team_recruitment_id, tag_id, deleted, create_time, update_time) VALUES (3, 1, 3, 1, '2020-03-15 18:21:06', '2020-03-15 13:43:23');
INSERT INTO ut.teams_recruitments_tags (id, team_recruitment_id, tag_id, deleted, create_time, update_time) VALUES (4, 1, 1, 1, '2020-03-18 17:33:36', '2020-03-15 13:46:00');
INSERT INTO ut.teams_recruitments_tags (id, team_recruitment_id, tag_id, deleted, create_time, update_time) VALUES (5, 1, 2, 1, '2020-03-18 17:33:36', '2020-03-15 13:46:00');
INSERT INTO ut.teams_recruitments_tags (id, team_recruitment_id, tag_id, deleted, create_time, update_time) VALUES (6, 1, 1, 0, '2020-03-18 17:35:06', '2020-03-15 13:46:00');
INSERT INTO ut.teams_recruitments_tags (id, team_recruitment_id, tag_id, deleted, create_time, update_time) VALUES (7, 1, 2, 0, '2020-03-18 17:35:06', '2020-03-15 13:46:00');
INSERT INTO ut.teams_recruitments_tags (id, team_recruitment_id, tag_id, deleted, create_time, update_time) VALUES (8, 1, 3, 0, '2020-03-18 17:35:06', '2020-03-15 13:46:00');