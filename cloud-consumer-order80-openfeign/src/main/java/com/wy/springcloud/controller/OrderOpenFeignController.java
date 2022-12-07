package com.wy.springcloud.controller;

import com.wy.springcloud.entities.CommonResult;
import com.wy.springcloud.entities.Payment;
import com.wy.springcloud.service.PaymentOpenFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderOpenFeignController {

    @Resource
    private PaymentOpenFeignService paymentOpenFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentOpenFeignService.getPaymentById(id);
    }

    //测试OpenFeign超时控制
    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeout(){
        //openFeign底层还是ribbon实现，客户端一般默认等待时长为 1s
        return paymentOpenFeignService.paymentFeignTimeout();
    }

}
