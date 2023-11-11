package io.github.greenhandsw.payeureka.controller;

import io.github.greenhandsw.core.controller.BaseController;
import io.github.greenhandsw.core.entity.Payment;
import io.github.greenhandsw.payeureka.repository.PaymentRepository;
import io.github.greenhandsw.payeureka.service.PaymentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("pay")
@RequestMapping("/pay")
public class PaymentController extends BaseController<Payment, PaymentService, Long, PaymentRepository> {
}
