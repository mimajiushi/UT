create table user
(
    uid          bigint unsigned auto_increment
        primary key,
    openid       varchar(50)  default '' not null,
    password     varchar(255) default '' null,
    nickname     varchar(255) default '' not null,
    sex          tinyint(2)              not null comment '0-保密 1-男 2-女',
    avatar       varchar(255)            null,
    phone_number varchar(11)  default '' not null,
    roles        int                     not null,
    email        varchar(255) default '' null,
    description  varchar(100) default '' null,
    deleted      tinyint(2)              not null,
    create_time  datetime                not null,
    update_time  datetime                not null on update CURRENT_TIMESTAMP
)
    charset = utf8mb4;

create index email
    on user (email);

create index wechat_id
    on user (openid);

INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('', '', 'admin', 0, 'https://www.wenjie.store/ut/img%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg', '15920323196', 8, ' ', '', 0, '2020-03-11 19:18:36', '2020-03-26 11:59:30');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('onjYF5ldIp8BdssDb9yXeHVFC7hs', '', 'chenwenjie', 1, 'https://www.wenjie.store/ut/img/wx0520203ba32fb247.o6zAJsz0ACQSiymv7WpL_Ncs5zEA.Pv9aqOfrBAXWc83c9290c4c3ad30d3119c618cc1682c_1585017012156.png', '15521245562', 13, '1498780478@qq.com', '1.曾参与过xxx项目，项目已上线，获得过xxx奖。
2.曾参与过xxx项目，项目已上线，获得过xxx奖。
3. 曾参与过xxx项目，项目已上线，获得过xxx奖', 0, '2020-03-09 13:41:30', '2021-03-10 15:50:40');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('', '', 'UT_8B1234', 0, 'https://www.wenjie.store/ut/img%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg', '13533533208', 0, ' ', '', 0, '2020-03-11 19:18:36', '2021-03-10 15:00:24');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('', '', 'UT_8B1266', 0, 'https://www.wenjie.store/ut/img%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg', '13533533207', 10, ' ', '', 0, '2020-03-03 19:18:36', '2020-03-26 11:59:21');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('', '', 'UT_8B1265', 0, 'https://www.wenjie.store/ut/img%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg', '13533533206', 1, ' ', '', 0, '2020-03-03 19:18:36', '2020-03-26 11:59:22');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('', '', 'UT_8B1264', 0, 'https://www.wenjie.store/ut/img%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg', '13533533205', 1, ' 1599603313@qq.com', '', 0, '2020-03-03 19:18:36', '2020-04-06 22:37:12');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('', '', 'UT_8B1263', 0, 'https://www.wenjie.store/ut/img%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg', '13533533204', 1, ' ', '', 0, '2020-03-03 19:18:36', '2020-03-26 11:59:24');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('', '', 'UT_8B1262', 0, 'https://www.wenjie.store/ut/img%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg', '13533533203', 1, ' ', '', 0, '2020-03-03 19:18:36', '2020-03-26 11:59:25');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('', '', 'UT_8B1261', 0, 'https://www.wenjie.store/ut/img%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg', '13533533202', 1, ' ', '', 0, '2020-03-03 19:18:36', '2020-03-26 11:59:26');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('', '', 'UT_8B1260', 0, 'https://www.wenjie.store/ut/img%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg', '13533533201', 1, ' ', '', 0, '2020-03-03 19:18:36', '2020-03-26 11:59:27');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('', '', 'Lucien', 1, 'https://www.lushihuan.com/wp-content/uploads/2020/02/144.png', '13543191525', 13, '1358046425@qq.com', '', 0, '2020-03-09 13:41:30', '2020-05-25 21:23:02');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('onjYF5l3TPAg-8fDj11SDxFEpZU0', '', 'wallee59', 1, 'https://www.wenjie.store/ut/img/wx0520203ba32fb247.o6zAJswzhruDfQXgQQfYqlsf0qSI.qXFSoJetUZZC0e7425d80d248c8671f44a3daf5516f8_1589990875919.jpg', '', 13, '920576696@qq.com', '但是士大夫大顶顶顶顶顶顶顶顶顶顶顶顶顶顶', 0, '2020-05-13 23:14:45', '2020-06-18 09:44:26');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('onjYF5jZ97G5dcNoAL2lE5vH0NeY', '', 'fengfeng', 0, 'https://wx.qlogo.cn/mmopen/vi_32/bSNKPNc3V7KG9PNdHAiaG6ljMAHHOGIf6n32YXRlWOE75LVKNY2B6c4pdtpCbkSlcQIaqXRuI5S2yIgVgnIsseQ/132', '', 13, '464912492@qq.com', '', 0, '2020-06-05 13:02:49', '2020-06-09 16:20:15');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('onjYF5sslr0yjxon88aOUTO91m6s', '', '∞', 0, 'https://wx.qlogo.cn/mmopen/vi_32/jNprAdUAcK2X6mL49h6cwwic6WeROlwVF7eeOYxXia03a287SwiaT4uG9ywagqofibzUavO4zzia3BPzZy19swRwL8A/132', '', 1, '', '', 0, '2020-06-10 18:35:00', '2020-06-10 18:34:59');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('onjYF5s1asobQsAPUQLctt7AyFl0', '', 'che', 1, 'https://wx.qlogo.cn/mmopen/vi_32/8iagWkricrrCf0SB8w4vbic7sR29CvD9qRepPp58icrgJSevqeG7qia0vNf6eVKhqfW5BHkDicpHC1zK9ZPVt5etF0pg/132', '', 1, '', '', 0, '2020-06-20 16:05:26', '2020-06-28 14:32:52');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('onjYF5vOTzp1hEU8xSFZwXwdThHc', '', 'joy', 0, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI9twgyaM3YKAIEJ3vPqVYxtRgiapibd2LHMpibrYTtTD4VEQxpplIfOW9CTHMkvUG1aD7xJDETF6icbg/132', '', 1, '', '', 0, '2020-06-26 20:31:19', '2020-06-26 21:45:09');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('onjYF5rtYJmDUInEBDIid1EUITH0', '', '刘培胜', 0, 'https://wx.qlogo.cn/mmopen/vi_32/TsRayWLjC9zribQweh4eIRXniaUFOzN7T7KgWWGaS7mVic9oGAA95dib7jX7gnbBvObC74ibwKX6usSibqqOW6xa9v8A/132', '', 1, '', '', 0, '2020-06-30 16:28:45', '2020-06-30 19:55:39');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('onjYF5tg_CA3-Snsd42R1IKlq4rI', '', '炯鑫', 0, 'https://wx.qlogo.cn/mmopen/vi_32/Dibeico9wDRCl9VGQLdvZ9Wb6ibuaTibsu6ia280QicOTOcQz9QwC0uFpDZsa2JM1064xR6kvQz6wMEzBX0msJYjIS7A/132', '', 1, '', '', 0, '2020-07-07 14:19:56', '2020-07-08 11:14:46');
INSERT INTO ut.user (openid, password, nickname, sex, avatar, phone_number, roles, email, description, deleted, create_time, update_time) VALUES ('onjYF5gO0LNdEHkobowoWOelO_Ps', '', '陈w', 0, 'https://www.wenjie.store/ut/img/2021-02-08-23-51-54_0_1614871692636.png', '', 1, '', 'hdoajowda', 0, '2021-03-04 23:28:01', '2021-03-06 07:52:47');