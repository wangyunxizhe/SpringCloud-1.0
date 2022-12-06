package com.wy.springcloud.controller;

import com.wy.springcloud.entities.CommonResult;
import com.wy.springcloud.entities.Payment;
import com.wy.springcloud.lb.MySelfLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //单机版 点对点发送
//    public static final String PAYMENT_URL = "http://localhost:8001";
    //集群版 负载均衡
    public static final String PAYMENT_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private MySelfLoadBalancer mySelfLoadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            log.info("StatusCode:{}", entity.getStatusCode());
            return entity.getBody();
        } else {
            return new CommonResult<>(444, "操作失败", null);
        }
    }

    /**
     * 测试自定义的轮询算法
     * 注意：调用前注释掉ApplicationContextConfig配置类中的@LoadBalanced注解，避免跟Ribbon自身的负载策略引发冲突报错
     */
    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentMyLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        if (CollectionUtils.isEmpty(instances)) {
            return null;
        }
        //根据自定义的轮询算法，获得需要调用的provider实例
        ServiceInstance serviceInstance = mySelfLoadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }

}
