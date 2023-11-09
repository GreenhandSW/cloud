package io.github.greenhandsw.core.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
public abstract class BaseEntity extends PersistableEntity<Long> implements Serializable {
}
