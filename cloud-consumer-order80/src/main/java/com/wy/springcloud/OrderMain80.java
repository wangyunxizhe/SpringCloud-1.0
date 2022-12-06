package com.wy.springcloud;

import com.wy.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
//调用provider（cloud-payment-service）时，使用自定义的Ribbon规则（规则制定在自定义的配置类MySelfRule中）
//@RibbonClient(name = "cloud-payment-service",configuration = MySelfRule.class)
public class OrderMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }

}
