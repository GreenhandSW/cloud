package io.github.greenhandsw.payeureka.controller;

import io.github.greenhandsw.core.controller.BaseController;
import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.common.entity.Payment;
import io.github.greenhandsw.payeureka.repository.PaymentRepository;
import io.github.greenhandsw.payeureka.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController("pay")
@RequestMapping("/pay")
public class PaymentController extends BaseController<Payment, PaymentService, Long, PaymentRepository> {
    private final AtomicInteger requestCount=new AtomicInteger(0);
    @RequestMapping("/timeout")
    public CommonResult<String> timeout(@RequestBody Long sleepMilliseconds) throws InterruptedException {
        assert 0<=sleepMilliseconds && sleepMilliseconds<=10000;
        log.info("sleep for {} ms", sleepMilliseconds);
        Thread.sleep(sleepMilliseconds);
        int count=requestCount.incrementAndGet();
        doLog(String.format("%s 睡眠时间：%s 调用次数：%d", registration.getInstanceId(), sleepMilliseconds, count), null, null);
        return new CommonResult<>(200, String.format("%s 睡眠时间：%s 调用次数：%d", registration.getInstanceId(), sleepMilliseconds, count), "没有超时");
    }
}
