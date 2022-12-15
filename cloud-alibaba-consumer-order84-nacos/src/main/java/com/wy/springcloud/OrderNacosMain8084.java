package com.wy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient//服务发现
@EnableFeignClients//激活Feign功能
public class OrderNacosMain8084 {

    public static void main(String[] args) {
        SpringApplication.run(OrderNacosMain8084.class, args);
    }

}
