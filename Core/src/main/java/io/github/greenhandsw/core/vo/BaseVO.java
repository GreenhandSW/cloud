package io.github.greenhandsw.core.vo;

import java.io.Serializable;

public interface BaseVO<ID extends Serializable> extends Serializable {
    void setId(ID id);

    ID getID();

    default void setVOID(Serializable id){
        this.setId((ID) id);
    }
}
