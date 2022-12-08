package com.wy.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wy.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
//使用场景：当大部分方法都需要异常兜底方法时，使用全局默认兜底方法，可以避免写很多兜底方法
@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    String paymentInfoOK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfoOK(id);
        return result;
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(
            //使用HystrixCommand注释，指定失败兜底降级方法为paymentInfoTimeoutHandler
            fallbackMethod = "paymentInfoTimeoutHandler",
            //设置1.5秒为调用该方法的峰值，未达峰值正常返回，超过走降级方法
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")}
    )
    String paymentInfoTimeout(@PathVariable("id") Integer id) {
        //遇到运行报错时，同样也会转而触发fallback指定的方法
//        int i = 10 / 0;
        String result = paymentHystrixService.paymentInfoTimeout(id);
        return result;
    }

    //paymentInfoTimeout()的fallback兜底方法
    public String paymentInfoTimeoutHandler(Integer id) {
        return "我是消费者8080，对方支付系统繁忙，稍后重试。若自身报错，请检查";
    }

    @GetMapping("/consumer/payment/hystrix/global/{id}")
    @HystrixCommand
    String paymentInfoGlobal(@PathVariable("id") Integer id) {
        //遇到运行报错时，同样也会转而触发fallback指定的方法
        int i = 10 / 0;
        String result = paymentHystrixService.paymentInfoTimeout(id);
        return result;
    }

    //全局兜底方法
    public String paymentGlobalFallbackMethod() {
        return "我是全局兜底方法，请稍后重试";
    }

}
