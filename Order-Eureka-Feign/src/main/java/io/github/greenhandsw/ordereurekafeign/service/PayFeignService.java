package io.github.greenhandsw.ordereurekafeign.service;

import io.github.greenhandsw.core.entity.CommonResult;
import io.github.greenhandsw.core.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(value = "${pay.name}", path="/${pay.prefix}")
public interface PayFeignService {
    @PostMapping("/get")
    CommonResult<Payment> get(@RequestBody Long id);

    @PostMapping("/timeout")
    CommonResult<String> timeout();
}
