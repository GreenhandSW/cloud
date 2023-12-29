package io.github.greenhandsw.payeureka.service;

import io.github.greenhandsw.common.entity.Video;
import io.github.greenhandsw.core.service.BaseService;
import io.github.greenhandsw.payeureka.repository.VideoRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class VideoService extends BaseService<Video, Long> implements ApplicationRunner {
    @Resource
    private VideoRepository r;
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Value(value = "${video.play.count.total}")
    String totalPlay;

    @Value(value = "${video.play.count.hot}")
    String hotPlay;

    @Value("${video.all}")
    String videoBitMap;

    @Override
    public void afterSave(Video video){
        redisTemplate.opsForValue().setBit(videoBitMap, video.getId(), true);
    }

    @Override
    public Video find(Long id){
        String idStr=String.valueOf(id);
        Video res= (Video) redisTemplate.opsForValue().get(idStr);
        if(res==null){
            res=super.r.findById(id).orElse(null);
        }
        redisTemplate.opsForHash().increment(totalPlay, idStr, 1);
        redisTemplate.opsForZSet().incrementScore(hotPlay, idStr, 1);
        return res;
    }

    /**
     * 在查找完成后再执行，更新10分钟内的热门视频
     * @param id
     */
    private void updatePlayCountIn10M(Long id){

    }

    @Override
    public void run(ApplicationArguments args)  {
        AtomicReference<Integer> count= new AtomicReference<>(0);
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            r.findAllIds().forEach(id->{
                Boolean result= connection.stringCommands().setBit(videoBitMap.getBytes(), id, true);
                if(Boolean.TRUE.equals(result)){
                    count.getAndSet(count.get() + 1);
                }
            });
            return null;
        });
        log.info("总共加载了{}个视频ID", count.get());
    }
}
