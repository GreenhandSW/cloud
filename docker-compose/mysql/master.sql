create schema payment collate utf8mb4_unicode_ci;
use payment;

# 这个表主要是从普通用户的角度来记录行为，因此不包含对视频的管理等UP主行为
create table `behavior` (
    `id` bigint auto_increment COMMENT '行为编号',
    `user_id` bigint not null COMMENT '用户id',
    `video_id` bigint not null COMMENT '视频id',
    `source` varchar(10) COMMENT '从哪里访问这个视频的',
    `behavior_type` enum('visit', 'upvote', 'downvote', 'favorite', 'comment', 'play', 'follow', 'unfollow') COMMENT '用户行为：访问页面、点赞、点踩、收藏、播放、关注、取关',
    `create_time` time COMMENT '发布时间',
    PRIMARY KEY id(`id`) COMMENT '主键'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户行为记录表';

create user 'canal'@'%' identified with mysql_native_password by 'cloud';
# alter user 'canal'@'%' identified with mysql_native_password by 'cloud';
grant select, replication slave, replication client on *.* to 'canal'@'%';
delete from mysql.user where user='';
flush privileges;
