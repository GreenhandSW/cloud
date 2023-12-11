package io.github.greenhandsw.core.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import java.io.Serializable;

public interface BaseEntity<ID extends Serializable> {
    ID getId();

    void setId(ID id);
}
