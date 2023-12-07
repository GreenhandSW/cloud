https://forums.oracle.com/ords/apexds/post/jpa-mysql-sequence-table-created-when-use-generatedtype-aut-1643主要记录遇到的数据库问题及解决方法

# MySQL

### 问题

#### 1. 使用了联合主键无法设置其他键自增

创建一个表用于记录其他表中数据之间的关联关系，比如用户视频关系表记录用户和视频的关系，此时主键应当为用户和视频的关系，但同时希望保留一个键作为所有记录的编号。即如下的关系：

```mysql
`id` bigint not null auto_increment COMMENT 'id',
`user_id` bigint not null COMMENT '用户id',
`video_id` bigint not null COMMENT '视频id',
PRIMARY KEY user2video(`user_id`, `video_id`) COMMENT '用户与视频的关系是主键，是唯一的',

```

然而这样是不行的，会报错：there can be only one auto column and it must be defined as a key，意思是最多有一个自增列，并且这个列必须是一个键（比如主键、索引键等）。

这个问题除了按照要求重新把`id`设置为主键之外，还可以给`id`添加唯一索引来解决[^1]：

```mysql
UNIQUE KEY relation_id(`id`),
```

# JPA

### 知识

#### 1. 创建联合主键

例如为用户和视频的关系表建立（用户、视频）这样的联合主键：

1. 首先创建一个`UserVideoRelationKey`，依次放入联合主键的各列

   ```java
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class UserVideoRelationKey implements Serializable {
       private Long userId;
       private Long videoId;
   }
   ```

2. 然后在主类`UserVideoRelation`中，在类上添加注解，表示使用该类作为主键：

   ```java
   @IdClass(UserVideoRelationKey.class)
   ```

3. 最后需要在`UserVideoRelation`的`userId`和`videoId`列上添加`@Id`注解表示其是主键的一部分[^2][^7]。

#### 2. 各种索引[^6]

##### 2.1. 联合索引

在Entity类上加`@Table`注解：

```java
@Table(name = "video",
        indexes = {
                // 标题与播放量的索引
                @Index(name = "title2play_cnt", columnList = "title"),
                @Index(name = "title2play_cnt", columnList = "play_cnt"),
        }
)
```

##### 2.2. 唯一索引

```java
@Table(name = "video",
        uniqueConstraints = @UniqueConstraint(name = "user2videos", columnNames = {"user_id", "id"})
)
```

#### 3. 表关联方式（为了避免影响性能，弃用）

关联可以双向设置，也可以单向设置[^3]。由于多对多查询会消耗很多资源，因此尽量使用中间表将多对多转为多对一、一对多关系，另还有一对一关系[^4]。

<img src="/home/sw/Documents/code/idea/cloud/docs/assets/webp.webp" alt="img" style="zoom:50%;" />

单向设置方式（以多对一为例）[^5]：

首先多对一指的是多个本类对应一个其他类，比如有两个表，分别是用户表、用户视频关系表（用户是否点赞某个视频），一个用户可以产生多个用户视频关系，那么应当在用户视频关系表的用户id这里设置单向的多对一的关系。

```java
@ManyToOne(targetEntity = UserInfo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
@JoinColumn(referencedColumnName = "id", nullable = false, table = "user_info")
@Column(name = "user_id")
private String userId;
```

其中：

- `@ManyToOne`用于与表示多个本类对应一个其他类。需要指明对应的类、级联方式、是否懒加载等。
- `@JoinColumn`：设置对应表中的键、可否为空、关联表名

### 问题

#### 1. JPA自动为`xxx`表生成一个`xxx_seq`表作为自增id表

把`@GeneratedValue`注解修改为`@GeneratedValue(strategy = GenerationType.IDENTITY)`即可不再生成[^8]。



# 参考资料

[^1]: [there can be only one auto column and it must be defined as a key](https://blog.csdn.net/weixin_42364319/article/details/128568727)
[^2]: [JPA常用注解](https://blog.csdn.net/weixin_43657300/article/details/126741472)
[^3]: [JPA One-To-Many以及Many-To-One介绍](https://www.jianshu.com/p/1c279b221527)
[^4]: [Spring Data Jpa之外键注解 ](https://blog.csdn.net/weixin_46484046/article/details/132189054)
[^5]: [JPA表关联的方式@ManyToOne@JoinColumn](https://blog.csdn.net/viaco2love/article/details/124067166)
[^6]: [JPA创建主键索引、普通索引，组合索引，唯一索引](https://blog.csdn.net/fengyuyeguirenenen/article/details/124008103)
[^7]: [Composite Primary Keys in JPA ](https://www.baeldung.com/jpa-composite-primary-keys)
[^8]: [JPA & MySQL，使用GenerateType.AUTO时创建的SEQUENCE表](https://forums.oracle.com/ords/apexds/post/jpa-mysql-sequence-table-created-when-use-generatedtype-aut-1643)



