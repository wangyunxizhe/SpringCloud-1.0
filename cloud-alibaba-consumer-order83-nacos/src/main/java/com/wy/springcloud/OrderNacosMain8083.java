package com.wy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient//服务发现
public class OrderNacosMain8083 {

    public static void main(String[] args) {
        SpringApplication.run(OrderNacosMain8083.class, args);
    }

}
