create schema payment collate utf8mb4_unicode_ci;
use payment;
CREATE TABLE `payment`(
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `serial` VARCHAR(200) DEFAULT '',
    PRIMARY KEY(`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT '付款信息表';

create table `user_info` (
    `id` bigint not null COMMENT '用户唯一id',
    `phone` bigint not null COMMENT '用户电话号码，不可为空',
    `password` varchar(16) COMMENT '用户密码',
    PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户核心信息';

create table `user_data`(
    `id` bigint not null COMMENT '用户唯一id',
    `name` varchar(32) not null COMMENT '用户昵称',
    `follow_cnt` int default 0 COMMENT '用户关注了多少个其他用户',
    `followed_cnt` int default 0 COMMENT '用户受到多少个其他用户的关注',
    PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户数据表';

create table `user_relation`(
    `id` bigint not null auto_increment COMMENT 'id',
    `followed_id` bigint not null COMMENT '被关注用户的id',
    `follower_id` bigint not null COMMENT '关注了followed_id用户的用户的id',
    `create_time` time COMMENT '关注时间',
    PRIMARY KEY (`id`),
    INDEX followeds (follower_id, followed_id) COMMENT '用户的关注列表索引'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户关系表，记录用户之间的关注关系';

create table `user_video_relation`(
    `id` bigint not null auto_increment COMMENT 'id',
    `user_id` bigint not null COMMENT '用户id',
    `video_id` bigint not null COMMENT '视频id',
    `vote` tinyint(2) COMMENT '0为默认，1为点赞，2为点踩',
    `favorite` bool default false COMMENT '是否收藏视频',
    `create_time` time COMMENT '关系创建时间',
    PRIMARY KEY (`id`),
    INDEX user2video (user_id, video_id, vote) COMMENT '用户与某个视频的点赞/收藏关系'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户和视频的关系表，记录用户是否点赞、点踩、收藏某个视频';


create table `video` (
    `id` bigint not null COMMENT '视频id',
    `user_id` bigint not null COMMENT '发布者id',
    `create_time` time COMMENT '发布时间',
    `url` varchar(100) COMMENT '视频地址',
    `title` varchar(100) COMMENT '视频名称',
    `description` varchar(1000) COMMENT '视频描述信息',
    `seconds` int COMMENT '视频时长（秒）',
    `play_cnt` int default 0 COMMENT '播放量',
    `comment_cnt` int default 0 COMMENT '评论数量',
    `upvote_cnt` int default 0 COMMENT '点赞数量',
    `downvote_cnt` int default 0 COMMENT '点踩数量',
    `favorite_cnt` int default 0 COMMENT '被收藏数量',
    PRIMARY KEY (`id`),
    INDEX user2videos (user_id, id, create_time) COMMENT '发布者和视频的索引',
    INDEX title2time(title, create_time) COMMENT '标题和发布时间的索引',
    INDEX title2play_cnt(title, play_cnt) COMMENT '标题和播放量的索引',
    INDEX title2upvote_cnt(title, play_cnt) COMMENT '标题和点赞量的索引'
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '视频信息表，记录视频的主要信息';

create table `comment` (
    `id` bigint not null auto_increment COMMENT '评论id',
    `user_id` bigint not null COMMENT '评论发布者id',
    `video_id` bigint not null COMMENT '评论属于的视频id',
    `create_time` time COMMENT '评论创建时间',
    `upvote_cnt` int default 0 COMMENT '点赞数量',
    `downvote_cnt` int default 0 COMMENT '点踩数量',
    `content` varchar(1000) COMMENT '评论内容',
    PRIMARY KEY (`id`),
    INDEX video2comments (video_id, create_time, id),
    INDEX video2comments_by_upvote_cnt(video_id, upvote_cnt, id)
)ENGINE=INNODB  DEFAULT CHARSET=utf8mb4 COMMENT '评论信息表，记录评论的主要信息';

create table `favorite` (
    `id` bigint not null auto_increment COMMENT '收藏id',
    `user_id` bigint not null COMMENT '该收藏记录所属用户的id',
    `video_id` bigint not null COMMENT '收藏视频的id',
    `create_time` time COMMENT '收藏时间',
    PRIMARY KEY (`id`),
    INDEX favorites (user_id, video_id, create_time) COMMENT '用户收藏列表索引'
)ENGINE=INNODB  DEFAULT CHARSET=utf8mb4 COMMENT '收藏信息表，记录用户收藏的视频';

-- CREATE TABLE  `user` (
--     `id` int(10) unsigned NOT NULL COMMENT '主键',
--     `salt` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码、通信等加密盐',
--     `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码、通信等加密盐',
--
-- )

# select User, Host from mysql.user;
# update mysql.user set Host='%' where User='root';

# flush privileges;

create user 'canal'@'%' identified with mysql_native_password by 'cloud';
# alter user 'canal'@'%' identified with mysql_native_password by 'cloud';
grant select, replication slave, replication client on *.* to 'canal'@'%';
delete from mysql.user where user='';
flush privileges;