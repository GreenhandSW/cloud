package io.github.greenhandsw.common.entity;

import io.github.greenhandsw.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements BaseEntity<Long> {
    @Id
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "serial")
    private String serial;
}