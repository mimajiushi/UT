create table roles
(
    role_id     int               not null
        primary key,
    role_desc   varchar(32)       not null comment '角色（会员）中文描述',
    role_name   varchar(32)       not null comment '角色（会员）英文名',
    discount    decimal(10, 2)    not null comment '折扣',
    create_date datetime          not null,
    update_date datetime          not null,
    deleted     tinyint default 0 not null
)
    charset = utf8mb4;

create index discount
    on roles (discount);

INSERT INTO ut.roles (role_id, role_desc, role_name, discount, create_date, update_date, deleted) VALUES (1, '系统管理员', 'ROLE_ADMIN', 1.00, '2019-11-12 20:18:25', '2019-11-12 20:18:27', 0);
INSERT INTO ut.roles (role_id, role_desc, role_name, discount, create_date, update_date, deleted) VALUES (2, '游客', 'ROLE_TOURIST', 1.00, '2019-11-12 20:17:27', '2019-11-12 20:17:30', 0);
INSERT INTO ut.roles (role_id, role_desc, role_name, discount, create_date, update_date, deleted) VALUES (3, '学生', 'ROLE_STUDENT', 1.00, '2019-11-12 20:14:43', '2019-11-12 20:14:45', 0);
INSERT INTO ut.roles (role_id, role_desc, role_name, discount, create_date, update_date, deleted) VALUES (5, '导师', 'ROLE_TUTOR', 1.00, '2019-11-12 20:17:01', '2019-11-12 20:17:04', 0);
INSERT INTO ut.roles (role_id, role_desc, role_name, discount, create_date, update_date, deleted) VALUES (6, '活动主办方', 'ROLE_SPONSOR', 1.00, '2020-03-09 12:30:16', '2020-03-09 12:30:18', 0);