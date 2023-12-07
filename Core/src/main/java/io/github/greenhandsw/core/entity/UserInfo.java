package io.github.greenhandsw.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_info", uniqueConstraints = @UniqueConstraint(name = "phone", columnNames = {"phone"}))
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends BaseEntity {
    @Id
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "password")
    private String password;
}