create table options
(
    id           int auto_increment
        primary key,
    create_time  datetime                not null,
    deleted      tinyint      default 0  null,
    update_time  datetime                not null,
    option_key   varchar(100)            not null,
    option_value varchar(1023)           not null,
    remark       varchar(255) default '' null comment '备注',
    type         int          default 0  null
)
    engine = MyISAM
    charset = utf8mb4;

INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (1, '2020-01-05 22:50:05', 0, '2020-01-05 22:50:05', 'is_installed', 'true', '是否安装', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (2, '2020-01-12 10:53:39', 0, '2020-01-12 10:53:39', 'oss_qiniu_domain_protocol', 'https://', '七牛域名协议', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (3, '2020-01-12 10:53:39', 0, '2020-01-12 10:53:39', 'email_ssl_port', '465', 'Email端口', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (4, '2020-01-12 10:53:39', 0, '2020-01-12 10:53:39', 'oss_qiniu_zone', 'z2', '七牛地区', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (6, '2020-01-12 10:53:39', 0, '2020-02-23 15:43:12', 'email_enabled', 'true', '是否开启邮箱服务', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (7, '2020-01-12 10:53:39', 1, '2020-01-12 10:53:39', 'attachment_upload_max_parallel_uploads', '3', null, 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (8, '2020-01-12 10:53:39', 1, '2020-01-12 10:53:39', 'attachment_upload_max_files', '50', null, 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (9, '2020-01-12 10:53:39', 0, '2020-01-12 10:53:39', 'email_protocol', 'smtp', 'Email协议', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (10, '2020-01-12 10:53:39', 1, '2020-01-12 10:53:39', 'comment_api_enabled', 'true', null, 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (11, '2020-01-12 10:53:39', 1, '2020-01-12 10:53:39', 'comment_pass_notice', 'false', null, 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (13, '2020-01-12 10:53:39', 0, '2020-01-12 10:53:39', 'attachment_type', 'QINIUOSS', '附件存储类型', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (15, '2020-01-12 10:53:39', 0, '2020-01-12 10:53:39', 'oss_qiniu_domain', 'www.wenjie.store', '七牛绑定域名', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (16, '2020-01-12 10:53:39', 0, '2020-01-12 10:53:39', 'oss_qiniu_access_key', 'JGlXKLwD-FNx0oIqH5J2LnKSM_sWq7O9wxsxOsCz', '七牛Access Key', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (17, '2020-01-12 10:53:39', 0, '2020-01-12 10:53:39', 'oss_qiniu_secret_key', 'SsMRJKVT8d-wTmfpNH6_BzDNObbiRSk0-rfl4qjJ', '七牛Secret Key', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (18, '2020-01-12 10:53:39', 0, '2020-02-01 16:29:56', 'oss_qiniu_source', 'ut/img', '七牛文件目录', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (19, '2020-01-12 10:53:39', 0, '2020-01-12 10:53:39', 'oss_qiniu_bucket', 'img', '七牛空间名称', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (20, '2020-02-23 15:43:12', 0, '2020-02-23 15:44:17', 'email_host', 'smtp.163.com', 'STMP地址', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (21, '2020-02-23 15:43:12', 0, '2020-02-23 15:43:12', 'email_username', 'ut_test@163.com', 'Email地址', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (22, '2020-02-23 15:43:12', 0, '2020-02-23 15:51:53', 'email_password', 'PZEXZAQHOUKYZHGZ', 'Email授权码', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (23, '2020-02-23 15:43:12', 0, '2020-02-23 15:43:12', 'email_from_name', 'UT平台', '发件人名字', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (50, '2020-06-27 14:44:02', 0, '2020-06-27 14:44:00', 'test_key1', 'test_value11', '测试属性1', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (51, '2020-06-27 14:44:02', 0, '2020-06-27 14:44:00', 'test_key2', 'test_value22', '测试属性2', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (52, '2020-06-27 14:46:08', 0, '2020-06-27 14:46:09', 'test_key3', 'test_value33', '', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (53, '2020-07-10 18:50:45', 0, '2020-07-10 18:50:45', 'mp-app-id', 'wx0520203ba32fb247', '小程序appid', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (54, '2020-07-10 18:50:45', 0, '2020-07-10 18:50:45', 'mp-app-secret', 'a211d5b68dc57636ccf1c9dce638e9e3', '小程序secret', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (55, '2020-07-10 18:50:45', 0, '2020-07-10 18:50:45', 'authorize-url', 'https://api.weixin.qq.com/sns/jscode2session', 'authorize-url', 0);
INSERT INTO ut.options (id, create_time, deleted, update_time, option_key, option_value, remark, type) VALUES (56, '2020-07-10 18:50:45', 0, '2020-07-10 18:50:45', 'grant-type', 'authorization_code', 'grant-type', 0);