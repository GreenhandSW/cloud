package io.github.greenhandsw.payeureka.controller;

import io.github.greenhandsw.common.entity.UserInfo;
import io.github.greenhandsw.core.controller.BaseTmpController;
import io.github.greenhandsw.core.entity.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("userinfo")
@RequestMapping("/user/info")
public class UserInfoController extends BaseTmpController<UserInfo, Long> {

    @RequestMapping("/unregister")
    public CommonResult<String> unregister(@RequestBody UserInfo userInfo){
        return new CommonResult<>();
    }
}
