package io.github.greenhandsw.sync.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import io.github.greenhandsw.core.entity.BaseEntity;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor
public class CanalConsumer<Entity extends BaseEntity<ID>, ID extends Serializable> {

    private long timeout = 600L;

    public CanalConsumer(long timeout){
        this.timeout=timeout;
    }

    private static final Logger log= LoggerFactory.getLogger(CanalConsumer.class);
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(topics = "${kafka.topic}")
    public void receive(ConsumerRecord<?, ?> record) {
        String val = (String) record.value();
        log.info("topic名称:{}, key:{}, 分区:{}, 下标:{}, value:{}", record.topic(), record.key(), record.partition(), record.offset(), record.value());
        CanalEntity<Entity> canalEntity = JSON.parseObject(val, new TypeReference<>() {
        });
        boolean isDDL = canalEntity.isDdl();
        String type = canalEntity.getType();
        if (!isDDL) {
            List<Entity> entities = canalEntity.getData();
            Object result=null;
            if ("INSERT".equals(type)) {
                result=insert(entities);
            } else if ("UPDATE".equals(type)) {
                result= insert(entities);
            } else if ("DELETE".equals(type)) {
                result= delete(entities);
            }
            log.info("操作类型：{}；数据：{}；结果：{}", type, entities, result);
        }
    }

    public int insert(List<Entity> entities){
        RedisSerializer<String> serializer=redisTemplate.getStringSerializer();
        AtomicReference<Integer> count= new AtomicReference<>(0);
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            entities.forEach(entity->{
                        Boolean result= connection.stringCommands().set(
                                Objects.requireNonNull(serializer.serialize(String.valueOf(entity.getId()))),
                                Objects.requireNonNull(serializer.serialize(String.valueOf(entity))),
                                Expiration.seconds(timeout),
                                RedisStringCommands.SetOption.UPSERT
                        );
                        if(Boolean.TRUE.equals(result)){
                            count.getAndSet(count.get() + 1);
                        }
                    });
            return null;
        });
        return count.get();
    }

    public Long delete(List<Entity> entities){
        List<String> ids=entities.stream().map(entity -> String.valueOf(entity.getId())).toList();
        return redisTemplate.delete(ids);
    }
}
