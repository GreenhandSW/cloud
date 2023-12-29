package io.github.greenhandsw.sync.consumer;

import io.github.greenhandsw.common.entity.Payment;
import io.github.greenhandsw.sync.common.EntityConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class PlayCountConsumer extends EntityConsumer<Payment, Long> {
}
