主要记录项目设计思路及规划实现功能。
数据库设计：
1. 主要设计思路如下所示，但为了避免影响性能，按照阿里设计规范的要求，取消了所有的外键。

   ```mysql
   create table `user_info` (
       `id` bigint not null COMMENT '用户唯一id',
       `phone` bigint not null COMMENT '用户电话号码，不可为空',
       `password` varchar(16) COMMENT '用户密码',
       PRIMARY KEY user_id(`id`) COMMENT '用户id作为主键',
       UNIQUE INDEX phone (phone) COMMENT '号码索引方便登陆验证'
   )ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户核心信息';
   
   create table `user_data`(
       `id` bigint not null COMMENT '用户唯一id',
       `name` varchar(32) not null COMMENT '用户昵称',
       `follow_cnt` int default 0 COMMENT '用户关注了多少个其他用户',
       `followed_cnt` int default 0 COMMENT '用户受到多少个其他用户的关注',
       `create_time` time COMMENT '用户注册时间',
       PRIMARY KEY user_id(`id`) COMMENT '主键'
   #                         ,
   #     FOREIGN KEY user_id(`id`) REFERENCES user_info(`id`)
   )ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户数据表';
   
   create table `user_relation`(
       `id` bigint not null auto_increment COMMENT 'id',
       `followed_id` bigint not null COMMENT '被关注用户的id',
       `follower_id` bigint not null COMMENT '关注了followed_id用户的用户的id',
       `create_time` time COMMENT '关注时间',
       PRIMARY KEY (`follower_id`, `followed_id`),
       UNIQUE KEY (`id`),
   #     FOREIGN KEY followed_id(`followed_id`) REFERENCES user_info(`id`),
   #     FOREIGN KEY follower_id(`follower_id`) REFERENCES user_info(`id`),
       INDEX followed(`followed_id`) COMMENT '被关注用户的id，用于帮助快速查询用户受到哪些用户的关注'
   )ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户关系表，记录用户之间的关注关系';
   
   create table `video` (
       `id` bigint not null COMMENT '视频id',
       `user_id` bigint not null COMMENT '发布者id',
       `create_time` time COMMENT '发布时间',
       `cover_url` varchar(100) COMMENT '视频封面地址',
       `url` varchar(100) COMMENT '视频地址',
       `title` varchar(100) COMMENT '视频名称',
       `description` varchar(1000) COMMENT '视频描述信息',
       `seconds` int COMMENT '视频时长（秒）',
       `play_cnt` int default 0 COMMENT '播放量',
       `comment_cnt` int default 0 COMMENT '评论数量',
       `upvote_cnt` int default 0 COMMENT '点赞数量',
       `downvote_cnt` int default 0 COMMENT '点踩数量',
       `favorite_cnt` int default 0 COMMENT '被收藏数量',
       PRIMARY KEY video_id(`id`) COMMENT '视频编号作为主键',
   #     FOREIGN KEY publisher_id(`user_id`) REFERENCES user_info(`id`),
       UNIQUE INDEX user2videos (user_id, id, create_time) COMMENT '发布者和视频的索引',
       INDEX title2time(title, create_time) COMMENT '标题和发布时间的索引',
       INDEX title2play_cnt(title, play_cnt) COMMENT '标题和播放量的索引',
       INDEX title2upvote_cnt(title, play_cnt) COMMENT '标题和点赞量的索引'
   )ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '视频信息表，记录视频的主要信息';
   
   create table `user_video_relation`(
       `id` bigint not null auto_increment COMMENT 'id',
       `user_id` bigint not null COMMENT '用户id',
       `video_id` bigint not null COMMENT '视频id',
       `vote` enum('none', 'upvote', 'downvote') DEFAULT 'none' COMMENT '对视频的投票，分别为无、点赞、点踩',
       `favorite` bool default false COMMENT '是否收藏视频',
       `create_time` time COMMENT '关系创建时间',
       PRIMARY KEY user2video(`user_id`, `video_id`) COMMENT '用户与视频的关系是主键，是唯一的',
       UNIQUE KEY relation_id(`id`),
   #     FOREIGN KEY user_id(`user_id`) REFERENCES user_info(`id`),
   #     FOREIGN KEY video_id(`video_id`) REFERENCES video(`id`),
       UNIQUE INDEX user2video (video_id, user_id, vote, favorite) COMMENT '视频受到了哪些用户的点赞/收藏'
   )ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT '用户和视频的关系表，记录用户是否点赞、点踩、收藏某个视频';
   
   # 一个评论只能属于一个视频，而一个视频可以包含多个评论，
   # 因此视频和评论之间不存在多对多的关系，从而不需要创建额外的视频和评论的关系表
   create table `comment` (
       `id` bigint not null auto_increment COMMENT '评论id',
       `user_id` bigint not null COMMENT '评论发布者id',
       `video_id` bigint not null COMMENT '评论属于的视频id',
       `create_time` time COMMENT '评论创建时间',
       `upvote_cnt` int default 0 COMMENT '点赞数量',
       `downvote_cnt` int default 0 COMMENT '点踩数量',
       `content` varchar(1000) COMMENT '评论内容',
       PRIMARY KEY video2comment(`video_id`, `id`) COMMENT '每个视频下的评论编号是唯一的',
       UNIQUE KEY comment_id(`id`),
   #     FOREIGN KEY (`user_id`) REFERENCES user_info(`id`),
   #     FOREIGN KEY (`video_id`) REFERENCES video(`id`),
       UNIQUE INDEX video2comments (video_id, create_time, id),
       UNIQUE INDEX video2comments_by_upvote_cnt(video_id, upvote_cnt, id)
   )ENGINE=INNODB  DEFAULT CHARSET=utf8mb4 COMMENT '评论信息表，记录评论的主要信息';
   
   ```

热点数据缓存：

1. 加载视频的模块更新redis里的：
   1. 总播放量，通过redis的hashtable更新，需要加分布式锁
   2. 短时间内的热点播放量，通过redis的zset滑动窗口更新，需要加分布式锁
2. 加载评论的模块更新redis里的：
   1. 总评论量，通过redis的hashtable更新，需要加分布式锁
3. 部署redis集群避免统计数据丢失，同时需要开启aof，设置每秒或者每执行若干次更新就刷盘
4. 总播放量、总评论量通过一个存储模块从redis里读取写入到MySQL的对应表中，设计定时任务以降低对mysql的影响。



框架设计：

1. 数据一致性基本在数据库层面解决，框架能解决一部分但不完全
1. Controller单独一个模块，其他Service、Entity、Repository放在一个模块
2. Controller为实体类，实现Entity的泛型Controller接口，返回类型统一为Object
2. 
