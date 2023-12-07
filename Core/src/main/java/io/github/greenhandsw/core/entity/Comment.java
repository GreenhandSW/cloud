package io.github.greenhandsw.core.entity;

import io.github.greenhandsw.core.entity.key.VideoCommentRelationKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "comment", uniqueConstraints ={
        @UniqueConstraint(name = "video2comment", columnNames = {"video_id", "create_time", "id"}),
        @UniqueConstraint(name = "video2comment_by_upvote_cnt", columnNames = {"video_id", "upvote_cnt", "id"})}
)
@IdClass(VideoCommentRelationKey.class)
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

//    @ManyToOne(targetEntity = UserInfo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @Column(name = "user_id")
    private Long userId;

    @Id
//    @ManyToOne(targetEntity = Video.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "video_id", referencedColumnName = "id", nullable = false, table = "video")
    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "create_time")
    private Timestamp timestamp;

    @Column(name = "upvote_cnt")
    private int upvoteCount;

    @Column(name = "downvote_cnt")
    private int downvoteCount;

    @Column(name = "content")
    private String content;
}