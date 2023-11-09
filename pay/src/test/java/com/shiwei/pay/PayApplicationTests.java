package com.shiwei.pay;

import com.shiwei.pay.service.PaymentService;
import io.github.greenhandsw.core.entity.Payment;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class PayApplicationTests {
    @Resource
    private PaymentService paymentService;

    @Test
    void save(){
        Payment payment=new Payment();
        payment.setId(1000L);
        payment.setSerial("测试");
        paymentService.save(payment);
    }

    @Test
    void getById(){
        Payment payment=paymentService.find(1000L);
        log.info("查找到的信息是:" + payment);
    }

    void deleteById(){
        paymentService.delete(1000L);
    }

    @Test
    void contextLoads() {
    }

}
