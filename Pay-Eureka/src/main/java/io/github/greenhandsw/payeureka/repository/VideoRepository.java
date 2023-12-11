package io.github.greenhandsw.payeureka.repository;

import io.github.greenhandsw.common.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    public void deleteAllByUserId(Long userId);
}
