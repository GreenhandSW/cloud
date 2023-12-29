package io.github.greenhandsw.payeureka.service;

import io.github.greenhandsw.common.entity.UserRelation;
import io.github.greenhandsw.core.service.BaseService;
import io.github.greenhandsw.payeureka.repository.UserRelationRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class UserRelationService extends BaseService<UserRelation, Long> {
    @Resource
    private UserRelationRepository r;

    @Override
    public void delete(UserRelation relation){
        r.deleteByFollowedIdAndFollowerId(relation.getFollowedId(), relation.getFollowerId());
        afterDelete(relation);
    }
}
