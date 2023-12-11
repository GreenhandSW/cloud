package io.github.greenhandsw.payeureka.controller;

import io.github.greenhandsw.common.entity.UserInfo;
import io.github.greenhandsw.core.controller.BaseController;
import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.payeureka.repository.UserInfoRepository;
import io.github.greenhandsw.payeureka.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController("userinfo")
@RequestMapping("/user/info")
public class UserInfoController extends BaseController<UserInfo, UserInfoService, Long, UserInfoRepository> {

    @RequestMapping("/unregister")
    public CommonResult<String> unregister(@RequestBody UserInfo userInfo){
        return new CommonResult<>();
    }
}
