package com.wy.springcloud.service;

import com.wy.springcloud.entities.CommonResult;
import com.wy.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 利用Feign给调用指定的provider做降级策略，与业务代码解耦，让PaymentServiceFallback专门负责fallback的方法们
 */
@FeignClient(value = "nacos-payment-provider", fallback = PaymentServiceFallback.class)
public interface PaymentService {

    @GetMapping(value = "/paymentSQL/{id}")
    CommonResult<Payment> paymentSQL(@PathVariable("id") Long id);

}
