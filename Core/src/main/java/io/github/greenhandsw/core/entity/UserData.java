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
@Table(name = "user_data")
@AllArgsConstructor
@NoArgsConstructor
public class UserData extends BaseEntity {
    @Id
//    @OneToOne(targetEntity = UserInfo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, mappedBy = "id", orphanRemoval = true)
//    @JoinColumn(name = "id", referencedColumnName = "id", unique = true, nullable = false, foreignKey = @ForeignKey(name = ))
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
