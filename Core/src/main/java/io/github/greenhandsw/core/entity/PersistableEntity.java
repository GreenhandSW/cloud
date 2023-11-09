package io.github.greenhandsw.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@MappedSuperclass
public abstract class PersistableEntity<ID extends Serializable> implements Persistable<ID>, Serializable {
    @Serial
    private static final long serialVersionUID=1L;

    @JsonIgnore
    @Transient
    public boolean isNew() {
        return null == getId();
    }
}
