package com.wy.springcloud.service;

import com.wy.springcloud.entities.CommonResult;
import com.wy.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallback implements PaymentService {

    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(44444, "服务降级返回,---PaymentFallbackService",
                new Payment(id, "errorSerial"));
    }

}
