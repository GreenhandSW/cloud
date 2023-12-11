package io.github.greenhandsw.payeureka.service;

import io.github.greenhandsw.common.entity.Video;
import io.github.greenhandsw.core.service.BaseService;
import io.github.greenhandsw.payeureka.repository.VideoRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class VideoService extends BaseService<Video, Long, VideoRepository> {
    @Resource
    private VideoRepository r;
}
