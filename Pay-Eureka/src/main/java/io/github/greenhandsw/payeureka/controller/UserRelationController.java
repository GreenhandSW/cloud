package io.github.greenhandsw.payeureka.controller;

import io.github.greenhandsw.common.entity.UserRelation;
import io.github.greenhandsw.core.controller.BaseTmpController;
import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.payeureka.service.UserRelationService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@RestController("userrelation")
@RequestMapping("/user/relation")
public class UserRelationController extends BaseTmpController<UserRelation, Long> {
    @Resource
    private UserRelationService s;

    @RequestMapping("/follow")
    public Object follow(@RequestBody UserRelation relation){
        relation.setTimestamp(Timestamp.from(Instant.now()));
        return super.add(relation);
    }

    @RequestMapping("/unfollow")
    public Object unfollow(@RequestBody UserRelation relation){
        s.delete(relation);
        doLog("取消关注", relation, null);
        return new CommonResult<>(200, "取消关注成功", null);
    }
}
