package io.github.greenhandsw.payeureka.controller;

import io.github.greenhandsw.core.controller.BaseController;
import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.core.entity.Payment;
import io.github.greenhandsw.payeureka.repository.PaymentRepository;
import io.github.greenhandsw.payeureka.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@RestController("pay")
@RequestMapping("/pay")
public class PaymentController extends BaseController<Payment, PaymentService, Long, PaymentRepository> {
    @RequestMapping("/timeout")
    public CommonResult<String> timeout() throws InterruptedException {
        int sleepTime=new Random().nextInt(5000);
        log.info("sleep for {} ms", sleepTime);
        Thread.sleep(sleepTime);
        doLog("超时", null, null);
        return new CommonResult<>(200, LocalDateTime.now().toString(), "没有超时");
    }
}
