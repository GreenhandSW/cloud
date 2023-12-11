package io.github.greenhandsw.common.entity;

import io.github.greenhandsw.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_data")
@AllArgsConstructor
@NoArgsConstructor
public class UserData implements BaseEntity<Long> {
    @Id
//    @OneToOne(targetEntity = UserInfo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, mappedBy = "id", orphanRemoval = true)
//    @JoinColumn(name = "id", referencedColumnName = "id", unique = true, nullable = false, foreignKey = @ForeignKey(name = ))
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="follow_cnt")
    private int followCount;

    @Column(name = "followed_cnt")
    private int followedCount;

    @Column(name = "create_time")
    private Timestamp timestamp;
}
