package io.github.greenhandsw.payeureka.service;

import io.github.greenhandsw.common.entity.Payment;
import io.github.greenhandsw.core.service.BaseService;
import io.github.greenhandsw.payeureka.repository.PaymentRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PaymentService extends BaseService<Payment, Long, PaymentRepository> {
    @Resource
    private PaymentRepository r;
}
