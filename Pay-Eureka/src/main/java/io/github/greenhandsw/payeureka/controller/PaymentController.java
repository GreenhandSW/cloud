package io.github.greenhandsw.payeureka.controller;

import io.github.greenhandsw.core.controller.BaseController;
import io.github.greenhandsw.core.entity.Payment;
import io.github.greenhandsw.payeureka.repository.PaymentRepository;
import io.github.greenhandsw.payeureka.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("pay")
@RequestMapping("/pay")
public class PaymentController extends BaseController<Payment, PaymentService, Long, PaymentRepository> {
}
