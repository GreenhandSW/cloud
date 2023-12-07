package io.github.greenhandsw.core.entity;

import io.github.greenhandsw.core.entity.key.UserVideoRelationKey;
import io.github.greenhandsw.core.option.Vote;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_video_relation", uniqueConstraints =
@UniqueConstraint(name = "user2video", columnNames = {"user_id", "video_id"})
)
@IdClass(UserVideoRelationKey.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserVideoRelation extends BaseEntity {
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