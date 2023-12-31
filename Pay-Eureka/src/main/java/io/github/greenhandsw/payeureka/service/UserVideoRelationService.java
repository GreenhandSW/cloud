package io.github.greenhandsw.payeureka.service;

import io.github.greenhandsw.common.entity.UserVideoRelation;
import io.github.greenhandsw.core.service.BaseService;
import io.github.greenhandsw.payeureka.repository.UserVideoRelationRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserVideoRelationService extends BaseService<UserVideoRelation, Long, UserVideoRelationRepository> {
    @Resource
    private UserVideoRelationRepository r;
}
