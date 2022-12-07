package com.wy.springcloud.controller;

import com.wy.springcloud.entities.CommonResult;
import com.wy.springcloud.entities.Payment;
import com.wy.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

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

    //***服务发现接口*** 测试
    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("~~~element:{}", element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            log.info("serviceId:{},host:{},port:{},uri:{}",
                    instance.getServiceId(), instance.getHost(), instance.getPort(), instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    //模拟接口处理时长3秒，便于消费者调用时测试OpenFeign的超时控制
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

}
