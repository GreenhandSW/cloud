package io.github.greenhandsw.payeureka.repository;

import io.github.greenhandsw.common.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    void deleteAllByUserId(Long userId);

    @Query("select id from Video")
    List<Long> findAllIds();
}
