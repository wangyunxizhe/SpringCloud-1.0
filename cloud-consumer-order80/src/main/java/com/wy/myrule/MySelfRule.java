package com.wy.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 如果需要替换Ribbon的负载均衡规则，这个自定义的配置类（MySelfRule）就不能放在@ComponentScan注解所扫描的包以及子包下，
 * 否则这个类会被所有的Ribbon客户端共享，达不到特殊化定制的目的。
 * 注意：@SpringBootApplication注解包含了@ComponentScan注解，所以这个配置类（MySelfRule）不能放在com.wy.springcloud包以及它的子包中
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule() {
        //Ribbon共有7种负载策略，默认是轮询。
        //此处将ribbon默认使用的 轮询策略 改为 随机策略
        return new RandomRule();
    }

}
