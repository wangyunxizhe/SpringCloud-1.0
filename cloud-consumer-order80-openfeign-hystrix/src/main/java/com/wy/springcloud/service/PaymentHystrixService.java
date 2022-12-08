package com.wy.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
//fallback = PaymentFallbackServiceImpl.class ===> 如果调用provider失败，由PaymentFallbackServiceImpl类中的方法来做兜底
@FeignClient(value = "CLOUD-PROVIDER-PAYMENT-HYSTRIX", fallback = PaymentFallbackServiceImpl.class)
public interface PaymentHystrixService {

    @GetMapping("/payment/hystrix/ok/{id}")
    String paymentInfoOK(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    String paymentInfoTimeout(@PathVariable("id") Integer id);

}
