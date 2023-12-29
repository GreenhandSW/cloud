package io.github.greenhandsw.payeureka.repository;

import io.github.greenhandsw.common.entity.UserVideoRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVideoRelationRepository extends JpaRepository<UserVideoRelation, Long> {

    void deleteAllByUserId(Long userId);
}
