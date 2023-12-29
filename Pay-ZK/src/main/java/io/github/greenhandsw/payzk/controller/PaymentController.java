package io.github.greenhandsw.payzk.controller;

import io.github.greenhandsw.core.controller.BaseTmpController;
import io.github.greenhandsw.core.entity.Payment;
import io.github.greenhandsw.payzk.repository.PaymentRepository;
import io.github.greenhandsw.payzk.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController("pay")
@RequestMapping("/pay")
public class PaymentController extends BaseTmpController<Payment, PaymentService, Long, PaymentRepository> {
    @Value("${server.port}")
    private String port;

    @PostMapping("/zookeeper")
    public String zookeeper(){
        log.info("zookeeper: {}: {}", port, UUID.randomUUID());
        return String.format("zookeeper: %s: %s", port, UUID.randomUUID());
    }
}
