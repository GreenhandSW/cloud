package io.github.greenhandsw.payeureka.repository;

import io.github.greenhandsw.common.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
