package io.github.greenhandsw.ordereureka.controller;

import io.github.greenhandsw.core.controller.BaseRestController;
import io.github.greenhandsw.core.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("order")
@RequestMapping("/customer/order")
@Slf4j
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class OrderController extends BaseRestController<Payment, Long> {
}
