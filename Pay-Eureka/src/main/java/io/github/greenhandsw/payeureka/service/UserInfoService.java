package io.github.greenhandsw.payeureka.service;

import io.github.greenhandsw.common.entity.UserInfo;
import io.github.greenhandsw.core.service.BaseService;
import io.github.greenhandsw.payeureka.repository.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoService extends BaseService<UserInfo, Long> {
    @Resource
    private UserInfoRepository r;

    @Resource
    private UserDataRepository udr;

    @Resource
    private UserRelationRepository urr;

    @Resource
    private UserVideoRelationRepository uvrr;

    @Resource
    private VideoRepository vr;

    @Resource
    private CommentRepository cr;

//    public boolean save(UserInfo userInfo){
//        r.save(userInfo);
//    }

    public UserInfo findByPhone(String phone){
        return r.findByPhone(phone);
    }


    private Long findIdByPhone(String phone){
        return r.findByPhone(phone).getId();
    }

    /**
     * 删除用户，仅仅删除其登陆信息和账户信息，但视频、关系、评论等保存
     * @param userInfo 用户信息
     * @return 成功删除
     */
    @Transactional
    public boolean unregister(UserInfo userInfo){
        Long id=findIdByPhone(userInfo.getPhone());
        r.deleteById(id);
        udr.deleteById(id);
        return true;
    }

    /**
     * 彻底删除用户的所有信息，包括其他用户的关注等。
     * @param id 用户id
     * @return 成功删除
     */
    public boolean deleteAllOfUser(Long id){
        r.deleteById(id);
        udr.deleteById(id);
        urr.deleteAllByFollowedIdOrFollowedId(id, id);
        vr.deleteAllByUserId(id);
        uvrr.deleteAllByUserId(id);
        cr.deleteAllByUserId(id);
        return true;
    }
}
