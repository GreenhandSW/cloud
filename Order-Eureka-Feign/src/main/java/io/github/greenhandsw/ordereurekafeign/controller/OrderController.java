package io.github.greenhandsw.ordereurekafeign.controller;

import io.github.greenhandsw.core.controller.BaseRestController;
import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.core.entity.Payment;
import io.github.greenhandsw.ordereurekafeign.service.PayFeignService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

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
    @RateLimiter(name = "pay")
    @CircuitBreaker(name = "pay", fallbackMethod = "fallback")
    @TimeLimiter(name = "pay", fallbackMethod = "fallback")
    public CompletableFuture<CommonResult<String>> timeoutCall(@RequestBody Long sleepMilliseconds){
        long begin=System.currentTimeMillis();
        CompletableFuture<CommonResult<String>> result=CompletableFuture.supplyAsync(()->service.timeout(sleepMilliseconds));
        long end=System.currentTimeMillis();
        log.info("调用耗时：{}\n", end-begin);
        return result;
    }

    public CompletableFuture<CommonResult<String>> fallback(@RequestBody Long sleepMilliseconds, TimeoutException e){
        log.info(e.toString());
        return CompletableFuture.completedFuture(new CommonResult<>(500, "超时了", "timeout"));
    }

    public CompletableFuture<CommonResult<String>> fallback(@RequestBody Long sleepMilliseconds, CallNotPermittedException e){
        log.info(e.toString());
        return CompletableFuture.completedFuture(new CommonResult<>(500, "挂多了", "fail"));
    }
}
