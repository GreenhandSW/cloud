package io.github.greenhandsw.sync;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import io.github.greenhandsw.sync.entity.CanalEntity;
import org.springframework.stereotype.Component;
import io.github.greenhandsw.core.entity.Payment;

import java.util.List;

@Slf4j
@Component
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class CanalConsumer {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(topics = "example")
    public void receive(ConsumerRecord<?, ?> record) {
        String val = (String) record.value();
        log.info("topic名称:{}, key:{}, 分区:{}, 下标:{}, value:{}", record.topic(), record.key(), record.partition(), record.offset(), record.value());
        CanalEntity<Payment> canalEntity = JSON.parseObject(val, new TypeReference<>() {
        });
        boolean isDDL = canalEntity.isDdl();
        String type = canalEntity.getType();
        if (!isDDL) {
            List<Payment> payments = canalEntity.getData();
            long timeout = 600L;
            if ("INSERT".equals(type)) {
                for (Payment payment : payments) {
                    String id = String.valueOf(payment.getId());
                    redisTemplate.opsForValue().set(id, JSONObject.toJSONString(payment), timeout);
                }
            } else if ("UPDATE".equals(type)) {
                for (Payment payment : payments) {
                    String id = String.valueOf(payment.getId());
                    redisTemplate.opsForValue().set(id, JSONObject.toJSONString(payment), timeout);
                }
            } else if ("DELETE".equals(type)) {
                for (Payment payment : payments) {
                    String id = String.valueOf(payment.getId());
                    redisTemplate.delete(id);
                }
            }
        }
    }
}
