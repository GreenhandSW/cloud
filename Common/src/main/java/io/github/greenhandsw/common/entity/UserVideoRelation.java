package io.github.greenhandsw.common.entity;

import io.github.greenhandsw.common.entity.key.UserVideoRelationKey;
import io.github.greenhandsw.common.entity.option.Vote;
import io.github.greenhandsw.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_video_relation", uniqueConstraints =
@UniqueConstraint(name = "user2video", columnNames = {"user_id", "video_id"})
)
@IdClass(UserVideoRelationKey.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserVideoRelation implements BaseEntity<Long> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Id
//    @ManyToOne(targetEntity = UserInfo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, table = "user_info")
    @Column(name = "user_id")
    private Long userId;

    @Id
//    @ManyToOne(targetEntity = Video.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "video_id", referencedColumnName = "id", nullable = false, table = "video")
    @Column(name = "video_id")
    private Long videoId;

    @Column(name = "vote")
    @Enumerated(value = EnumType.ORDINAL)
    private Vote vote;

    @Column(name = "favorite")
    private boolean favorite;

    @Column(name = "create_time")
    private Timestamp timestamp;
}