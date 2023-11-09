package io.github.greenhandsw.core.repository;

import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

@Order
public interface BaseRepository<ENTITY, ID extends Serializable> extends JpaRepository<ENTITY, ID> {
//    <T extends ENTITY> T save(T entity);

//    <T extends ENTITY> List<T> save(Iterable<T> entities);

    void delete(ID id);

//    void delete(ID[] ids);

//    void delete(ENTITY entity);

//    void delete(Iterable<? extends ENTITY> entities);

    long count();

//    boolean exists(ID id);

    ENTITY findOne(ID id);

//    List<ENTITY> findAll(Iterable<ID> ids);
//
//    List<ENTITY> findAll(ID[] ids);
//
//    List<ENTITY> findAll();
//
//    List<ENTITY> findAll(Sort sort);
//
//    Page<ENTITY> findAll(Pageable pageable);
//
//    <S extends ENTITY> List<S> saveAll(Iterable<S> entities);
}
