create table activity_classify
(
    id          bigint      not null,
    cname       varchar(35) not null,
    create_time datetime    not null,
    update_time datetime    not null,
    deleted     tinyint     not null,
    constraint activity_classify_id_uindex
        unique (id),
    constraint activity_classify_name_uindex
        unique (cname)
)
    comment '活动分类';

alter table activity_classify
    add primary key (id);

INSERT INTO ut.activity_classify (id, cname, create_time, update_time, deleted) VALUES (1, '测试', '2021-03-10 14:02:55', '2021-03-10 14:02:58', 0);