create table user_comments
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

