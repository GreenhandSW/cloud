package io.github.greenhandsw.ordereurekafeign.controller;

import io.github.greenhandsw.core.controller.BaseRestController;
import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.core.entity.Payment;
import io.github.greenhandsw.ordereurekafeign.service.PayFeignService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("${order.name}")
@RequestMapping("/${order.prefix}")
@Slf4j
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class OrderController extends BaseRestController<Payment, Long> {
    @Resource
    private PayFeignService service;

    @PostMapping("/get")
    @Override
    public CommonResult<Payment> get(@RequestBody Long id) {
        return service.get(id);
    }

    @PostMapping("/timeout")
    public CommonResult<String> timeout(){
        return service.timeout();
    }
}
