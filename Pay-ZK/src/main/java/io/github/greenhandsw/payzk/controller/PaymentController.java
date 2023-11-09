package io.github.greenhandsw.payzk.controller;

import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.core.entity.Payment;
import io.github.greenhandsw.payzk.service.PaymentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController("pay")
@RequestMapping("/pay")
public class PaymentController {
    @Resource
    private PaymentService service;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping( "/add")
    public CommonResult<Payment> add(@RequestBody Payment payment){
        Payment result=service.save(payment);
        log.info(">>>>插入结果>>>>:"+ result);
        return new CommonResult<>(200, "插入成功", result);
    }

    @PostMapping("/get")
    public CommonResult<Payment> getById(@RequestBody Long id){
        Payment payment=service.find(id);
        log.info("*****查询结果: "+ payment);
        if(payment!=null){
            return new CommonResult<>(200, "查询成功", payment);
        }
        return new CommonResult<>(444, "查询失败，没有该记录: "+id);
    }

    @PostMapping("/discovery")
    public Object discovery(){
        // 获取服务列表的信息
        List<String> services=discoveryClient.getServices();
        for (String service :
                services) {
            log.info("******service: " + service);
        }
        // 获取所有实例
        List<ServiceInstance> instances=discoveryClient.getInstances("pay");
        for (ServiceInstance instance :
                instances) {
            log.info("{}\t{}:{}/{}", instance.getServiceId(), instance.getHost(), instance.getPort(), instance.getUri());
        }

        return discoveryClient;
    }

    @Value("${server.port")
    private String port;

    @PostMapping("/zookeeper")
    public String zookeeper(){
        return String.format("%s: %s", port, UUID.randomUUID());
    }
}
