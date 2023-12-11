package io.github.greenhandsw.payeureka.service;

import io.github.greenhandsw.common.entity.UserData;
import io.github.greenhandsw.core.service.BaseService;
import io.github.greenhandsw.payeureka.repository.UserDataRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserDataService extends BaseService<UserData, Long, UserDataRepository> {
    @Resource
    private UserDataRepository r;
}
