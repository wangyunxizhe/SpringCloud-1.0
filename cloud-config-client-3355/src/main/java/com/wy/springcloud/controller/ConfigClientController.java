package com.wy.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        //此处的${config.info}就是3355配置中心里配置的Gitee仓库中的config-xxx.yml中的配置信息
        //即：直接访问http://localhost:3355/configInfo，就可读到http://localhost:3344/master/config-dev.yml中的config.info的值
        return configInfo;
    }

}
