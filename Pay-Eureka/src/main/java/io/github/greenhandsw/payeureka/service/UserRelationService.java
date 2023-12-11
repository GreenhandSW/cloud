package io.github.greenhandsw.payeureka.service;

import io.github.greenhandsw.common.entity.UserRelation;
import io.github.greenhandsw.core.service.BaseService;
import io.github.greenhandsw.payeureka.repository.UserRelationRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserRelationService extends BaseService<UserRelation, Long, UserRelationRepository> {
    @Resource
    private UserRelationRepository r;
}
