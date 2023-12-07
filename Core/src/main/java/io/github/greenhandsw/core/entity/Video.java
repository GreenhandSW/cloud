package io.github.greenhandsw.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "video",
        uniqueConstraints = @UniqueConstraint(name = "user2videos", columnNames = {"user_id", "id"}),
        indexes = {
                // 标题与播放量的索引
                @Index(name = "title2play_cnt", columnList = "title"),
                @Index(name = "title2play_cnt", columnList = "play_cnt"),
                // 标题与点赞量的索引
                @Index(name = "title2upvote_cnt", columnList = "title"),
                @Index(name = "title2upvote_cnt", columnList = "upvote_cnt"),
        }
)
@AllArgsConstructor
@NoArgsConstructor
public class Video extends BaseEntity {
    @Id
    @Column(name = "id", unique = true)
    private Long id;

//    @ManyToOne(targetEntity = UserInfo.class, cascade = CascadeType.DETACH, fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, table = "user_info")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "create_time")
    private Timestamp timestamp;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "seconds")
    private String seconds;

    @Column(name = "play_cnt")
    private int playCount;

    @Column(name = "comment_cnt")
    private int commentCount;

    @Column(name = "upvote_cnt")
    private int upvoteCount;

    @Column(name = "downvote_cnt")
    private int downvoteCount;

    @Column(name = "favorite_cnt")
    private int favoriteCount;
}
