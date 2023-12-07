package io.github.greenhandsw.core.entity;


import io.github.greenhandsw.core.entity.key.UserRelationKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_relation", uniqueConstraints =
@UniqueConstraint(name = "followeds", columnNames = {"follower_id", "followed_id"})
)
@IdClass(UserRelationKey.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserRelation extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Id
//    @ManyToOne(targetEntity = UserInfo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "followed_id", referencedColumnName = "id", nullable = false, table = "user_info")
    @Column(name = "followed_id")
    private Long followedId;

    @Id
//    @ManyToOne(targetEntity = UserInfo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "follower_id", referencedColumnName = "id", nullable = false, table = "user_info")
    @Column(name = "follower_id")
    private Long followerId;

    @Column(name = "create_time")
    private Timestamp timestamp;
}