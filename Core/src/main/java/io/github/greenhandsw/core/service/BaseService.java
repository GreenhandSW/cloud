package io.github.greenhandsw.core.service;

import io.github.greenhandsw.core.repository.BaseRepository;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseService<T, ID extends Serializable, R extends JpaRepository<T, ID>> {
    // Repository
    @Resource
    protected R r;

    public <S extends T> S save(S entity){
        return this.r.save(entity);
    }

    public <S extends T> List<S> save(Iterable<S> entities){
        return r.saveAll(entities);
    }

    public T find(ID id){
        return r.findById(id).orElse(null);
    }

    public void delete(ID id){
        r.deleteById(id);
    }
}
