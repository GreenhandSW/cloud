package io.github.greenhandsw.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class BaseService<T, ID extends Serializable> {
    // Repository
    @Autowired
    public JpaRepository<T, ID> r;

    public <S extends T> void afterSave(S entity){}

    public <S extends T> void afterFind(S entity){}

    public void afterDelete(ID id){}

    public <S extends T> void afterDelete(S entity){}

    public <S extends T> void afterUpdate(S entity){}

    public <S extends T> void afterPage(Page<T> page){}

    public <S extends T> S save(S entity){
        S res=r.save(entity);
        afterSave(res);
        return res;
    }

    public <S extends T> List<S> save(Iterable<S> entities){
        return r.saveAll(entities);
    }

    public T find(ID id){
        T res= r.findById(id).orElse(null);
        afterFind(res);
        return res;
    }

    public Page<T> page(Pageable pageable){
        Page<T> res= r.findAll(pageable);
        afterPage(res);
        return res;
    }

    public void delete(ID id){
        r.deleteById(id);
        afterDelete(id);
    }

    public <S extends T> void delete(S entity){
        r.delete(entity);
        afterDelete(entity);
    }

    // 若需要用到必须重写！！！
    public <S extends T> S update(S entity){
        return null;
    }
}
