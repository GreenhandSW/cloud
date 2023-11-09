package io.github.greenhandsw.core.repository;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;

public class DefaultRepository<ENTITY, ID extends Serializable> extends SimpleJpaRepository<ENTITY, ID> implements BaseRepository<ENTITY, ID> {
    public DefaultRepository(JpaEntityInformation<ENTITY, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public DefaultRepository(Class<ENTITY> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
    }

    @Override
    public void delete(ID id) {
        super.deleteById(id);
    }

    @Override
    public ENTITY findOne(ID id) {
        return super.findById(id).orElse(null);
    }
}
