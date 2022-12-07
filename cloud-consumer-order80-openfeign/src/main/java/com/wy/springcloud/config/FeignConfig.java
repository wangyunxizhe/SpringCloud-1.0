package com.wy.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//OpenFeign日志增强 配置类
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
        //打开最全的日志
        return Logger.Level.FULL;
    }

}
