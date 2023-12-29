package io.github.greenhandsw.common.entity;

import io.github.greenhandsw.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_info", uniqueConstraints = @UniqueConstraint(name = "phone", columnNames = {"phone"}))
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "password")
    private String password;
}