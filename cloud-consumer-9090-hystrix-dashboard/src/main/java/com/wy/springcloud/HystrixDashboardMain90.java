package com.wy.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard//开启HystrixDashboard图形化界面
public class HystrixDashboardMain90 {

    /**
     * 启动后，通过http://ip:port/hystrix访问HystrixDashboard图形化Web界面，
     * 在界面中设置好需要监控的地址（本例中监控的样例是cloud-provider-pay8008-hystrix工程，地址为http://localhost:8008/hystrix.stream）
     * 以及别的参数，点击按钮即可开始监控目标工程
     */
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardMain90.class, args);
    }

}
