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
public abstract class EventConsumer<K extends Serializable, V> {

    private long timeout = 600L;

    public EventConsumer(long timeout){
        this.timeout=timeout;
    }

    private static final Logger log= LoggerFactory.getLogger(EventConsumer.class);
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void receive(ConsumerRecord<K, V> record) {
        K key= record.key();
        V val = record.value();
        log.info("topic名称:{}, key:{}, 分区:{}, 下标:{}, value:{}", record.topic(), record.key(), record.partition(), record.offset(), record.value());
        redisTemplate.opsForValue().increment((String) key, (Long) val);
//        log.info("操作类型：{}；数据：{}；结果：{}", type, entities, result);
    }
}
