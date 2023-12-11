package io.github.greenhandsw.payeureka.repository;

import io.github.greenhandsw.common.entity.UserRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRelationRepository extends JpaRepository<UserRelation, Long> {
    public int deleteAllByFollowedIdOrFollowedId(Long followedId, Long followerId);
}
