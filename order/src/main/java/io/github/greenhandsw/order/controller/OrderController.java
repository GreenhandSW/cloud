package io.github.greenhandsw.order.controller;

import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.core.entity.Payment;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController("order")
@RequestMapping("/customer/order")
@Slf4j
public class OrderController{
    @Resource
    private RestTemplate restTemplate;

    @Value("${pay.address}")
    private String payAddr;

    @Value("${pay.port}")
    private String payPort;

    @Value("${pay.prefix}")
    private String payPrefix;

    @PostMapping( "/add")
    public CommonResult<Payment> add(@RequestBody Payment payment){
        log.info(">>>>插入结果>>>>:"+ payment);

        return (CommonResult<Payment>) restTemplate.postForObject(paymentUrl()+"/add", payment, CommonResult.class);
    }

    @PostMapping("/get")
    public CommonResult<Payment> getById(@RequestBody Long id){
        log.info("*****查询的id: "+ id);

        return (CommonResult<Payment>) restTemplate.postForObject(paymentUrl()+"/get", id, CommonResult.class);
    }

    private String paymentUrl(){
        return String.format("http://%s:%s/%s", payAddr, payPort, payPrefix);
    }
}
