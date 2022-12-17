package com.wy.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.wy.springcloud.dao"})
public class MyBatisConfig {
}
