create table user_experiences
(
    id          bigint unsigned auto_increment
        primary key,
    uid         bigint unsigned         not null,
    name        varchar(255)            not null,
    awards      varchar(255) default '' null comment 'Just fill in the award',
    role        varchar(255) default '' null comment 'role in the project',
    project_url varchar(255) default '' null comment 'It can be GitHub or online URL and so on',
    description text                    null,
    start_time  varchar(50)             null,
    end_time    varchar(50)             null,
    deleted     tinyint(2)              not null,
    create_time datetime                not null,
    update_time datetime                not null on update CURRENT_TIMESTAMP
)
    charset = utf8mb4;

create index uid
    on user_experiences (uid);

INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, '智慧食堂', '三等奖', '后端开发', 'https://wenjie.store', '智慧食堂是一个xxx的系统 \\n 智慧食堂是一个xxx的系统', '2017-09-09', '2020-09-09', 0, '2020-03-11 23:32:09', '2020-03-16 19:39:18');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, 'UT平台', '一等奖', '后端开发', 'https://wenjie.store', 'UT平台是一个xxxxxxxxxx系统', '2017-09-09', '2020-09-09', 0, '2020-03-11 23:37:40', '2020-03-16 19:39:13');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, '校园物流', '二等奖', '后端开发', 'https://wenjie.store', '校园物流是一个xxxxx', '2017-09-09', '2020-09-09', 0, '2020-03-11 23:39:09', '2020-03-22 16:49:25');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, 'ut平台', '吹牛三等奖', '后端开发', 'https://wenjie.store', 'ut平台是一个xxx的系统', '2017-09-09', '2020-09-09', 1, '2020-03-12 15:06:31', '2020-03-12 16:14:27');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, 'ut平台1', '吹牛四等奖', '后端开发', 'https://wenjie.store', 'ut平台是一个xxx的系统', '2017-09-09', '2020-09-09', 1, '2020-03-12 15:10:10', '2020-03-12 23:38:34');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, 'ut平台2', '吹牛四等奖', '后端开发', 'https://wenjie.store', 'ut平台是一个xxx的系统', '2017-09-09', '2020-09-09', 1, '2020-03-12 15:10:25', '2020-03-12 23:38:32');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, '智慧食堂2', '吹牛三等奖', '后端开发', 'https://wenjie.store', '智慧食堂2是一个xxx的系统', '2017-09-09', '2020-09-09', 1, '2020-03-12 15:11:21', '2020-03-12 23:38:22');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, '智慧食堂3', '吹牛六等奖', '后端开发', 'https://wenjie.store', '智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3智慧食堂3', '2017-09-09', '2020-09-09', 1, '2020-03-12 15:11:44', '2020-03-12 23:38:29');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, '智慧食堂3', '吹牛七等奖', '后端开发', 'https://wenjie.store', 'xxxxxxxxxxxxxxxxx', '2017', '2018', 1, '2020-03-12 15:15:35', '2020-03-12 23:38:05');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, '智慧食堂3', '吹牛七等奖', '后端开发', 'https://wenjie.store', '项目描述', '2017', '2018', 1, '2020-03-12 15:17:28', '2020-03-12 23:38:17');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, '智慧食堂4', '吹牛一等奖', '后端开发', 'https://wenjie.store', '项目描述xxxxx', '2017-09-10', '2019', 1, '2020-03-12 15:18:06', '2020-03-12 23:38:27');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, '智慧物流', '吹牛三等奖', '后端开发', 'https://wenjie.store', 'dawdawdawdawdawd', '2019', '2020', 1, '2020-03-22 14:56:01', '2020-03-16 23:19:33');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, 'd', 'dada', 'ad', 'dad', 'ad', 'ada', 'adad', 1, '2020-03-22 16:07:00', '2020-03-16 23:14:17');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, 'adad', 'adad', 'adad', 'adad', 'd', 'ada', 'dad', 1, '2020-03-22 16:31:50', '2020-03-16 23:14:57');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, 'asd', 'asda', 'da', 'asd', 'asd', 'asd', 'asd', 1, '2020-03-22 16:32:25', '2020-03-16 23:15:33');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, 'dad', 'ada', 'asd', 'sda', 'asda', 'asdas', 'adads', 1, '2020-03-22 16:37:01', '2020-03-16 23:20:33');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, 'ad', 'asd', 'asd', 'asd', 'aasd', 'asda', 'das', 1, '2020-03-22 16:39:27', '2020-03-16 23:23:11');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, '好的吧不得不说', '不是宝宝树', '建党节就是', '八点半到吧', '你说你睡觉', '患得患失', '还上班不', 1, '2020-03-22 16:59:10', '2020-03-16 23:44:46');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (21, '智慧食堂4', '吹牛一等奖', '后端开发', 'https://wenjie.store', '项目描述xxxxx', '2019年10月', '2020年8月', 1, '2020-03-22 17:39:03', '2020-04-03 16:57:26');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (213, 'a', 'a', 'a', 'a', 'a', 'a', 'a', 1, '2020-05-14 06:41:05', '2020-05-16 12:40:57');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (213, 'a', 'a', 'a', 'a', 'a', 'a', 'a', 1, '2020-05-16 22:46:05', '2020-05-16 14:46:41');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (213, 'sad', 'asd', 'asd', 'asd', 'asdasd', 'asdasd', 'asd', 1, '2020-05-16 23:36:12', '2020-05-16 17:16:30');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (213, 'c''c', '从c', 'sd', 'sdsd', 'sd', 'sds', 'dsdd', 1, '2020-05-16 23:50:40', '2020-05-16 17:16:43');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (213, 'd', 'fdf', 'df', 'fdf', 'fdfdf', 'ffdf', 'fdf', 1, '2020-05-17 01:17:56', '2020-05-16 17:18:32');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (213, 'as', 'sdsd', 'sdsd', 'sdsd', 'sdsd', 'dsdsd', 'sds', 1, '2020-05-17 01:28:32', '2020-05-16 17:29:12');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (213, '是的s', 'd''s''d', '是多少', 'sdsf', 'dfdf', 'dfdfd', 'fdfdf', 0, '2020-05-17 06:47:40', '2020-05-16 22:48:11');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (213, '还跟你', '明细账', '嘻嘻嘻嘻', '找实习和', 'Mr话找实习', '一下子', '你希望', 1, '2020-05-17 22:31:27', '2020-06-07 21:17:55');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (213, 'rer', 'rtr', 'etrtr', 'tret', 'tret', 'eter', 'retr', 1, '2020-05-17 22:32:41', '2020-05-17 23:44:34');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (1367497062361047042, '智慧是让', '三等奖', 'ld', 'baidu.com', '这是一个xxx项目', '2020-02', '2020-10', 1, '2021-03-05 00:09:51', '2021-03-05 23:58:46');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (1367497062361047042, 'xx项目', 'xx等奖', 'ld', 'baidu.com', 'gigig', '2020-02', '2222-22', 1, '2021-03-05 00:29:30', '2021-03-05 23:58:41');
INSERT INTO ut.user_experiences (uid, name, awards, role, project_url, description, start_time, end_time, deleted, create_time, update_time) VALUES (1367497062361047042, 'XX杯', '一等奖', 'ld', 'baidu.com', '项目背景XXX
技术难点XXX
DAU提升XXX', '2021-03-01', '2022-03-02', 0, '2021-03-06 00:00:15', '2021-03-06 09:03:59');