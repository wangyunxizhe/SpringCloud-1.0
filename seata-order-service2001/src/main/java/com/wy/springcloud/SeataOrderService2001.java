package com.wy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * exclude = DataSourceAutoConfiguration.class 取消数据源的自动创建,
 * 读取自定义的DataSourceProxyConfig.class类，使用Seata对数据源进行代理
 *
 * @description 支付服务
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SeataOrderService2001 {

    public static void main(String[] args) {
        SpringApplication.run(SeataOrderService2001.class, args);
    }

}
