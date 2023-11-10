package io.github.greenhandsw.orderzk.controller;

import io.github.greenhandsw.core.controller.BaseRestController;
import io.github.greenhandsw.core.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("order")
@RequestMapping("/customer/order")
@Slf4j
public class OrderController extends BaseRestController<Payment, Long> {

    @PostMapping("/zookeeper")
    public String zookeeper() {
        return restTemplate.postForObject(url + "/zookeeper", null, String.class);
    }
}
