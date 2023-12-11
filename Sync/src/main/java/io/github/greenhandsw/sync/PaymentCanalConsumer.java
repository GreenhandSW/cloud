package io.github.greenhandsw.sync;

import io.github.greenhandsw.sync.common.CanalConsumer;
import io.github.greenhandsw.common.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class PaymentCanalConsumer extends CanalConsumer<Payment, Long> {
}
