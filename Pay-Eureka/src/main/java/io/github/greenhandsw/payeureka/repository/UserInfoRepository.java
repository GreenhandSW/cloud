package io.github.greenhandsw.payeureka.repository;

import io.github.greenhandsw.common.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByPhone(String phone);
}
