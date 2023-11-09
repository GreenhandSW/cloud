package com.shiwei.pay.service;

import com.shiwei.pay.repository.PaymentRepository;
import io.github.greenhandsw.core.entity.Payment;
import io.github.greenhandsw.core.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PaymentService extends BaseService<Payment, Long, PaymentRepository> {
    @Resource
    private PaymentRepository r;
}
