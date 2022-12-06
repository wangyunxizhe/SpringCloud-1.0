package com.wy.springcloud.controller;

import com.wy.springcloud.entities.CommonResult;
import com.wy.springcloud.entities.Payment;
import com.wy.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("~~~ create结果：{} ~~~", result);
        if (result > 0) {
            return new CommonResult(200, "create成功，serverPort：" + serverPort, result);
        } else {
            return new CommonResult(444, "create失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("~~~ getPaymentById：{} ~~~", payment);
        if (payment != null) {
            return new CommonResult(200, "getPaymentById成功，serverPort：" + serverPort, payment);
        } else {
            return new CommonResult(444, "id:" + id + "，未查询到对应记录", null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

}
