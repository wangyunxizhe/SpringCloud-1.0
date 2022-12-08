package com.wy.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * 该类存在的意义（使用场景）：consumer调用provider时，provider正好宕机或关闭。为了达到“解耦”目的，
 * 编写此实现类专门应对类似情况做consumer端的服务降级
 */
@Component
public class PaymentFallbackServiceImpl implements PaymentHystrixService{

    @Override
    public String paymentInfoOK(Integer id) {
        return "----- PaymentFallbackServiceImpl fall back paymentInfoOK -----";
    }

    @Override
    public String paymentInfoTimeout(Integer id) {
        return "----- PaymentFallbackServiceImpl fall back paymentInfoTimeout -----";
    }
}
