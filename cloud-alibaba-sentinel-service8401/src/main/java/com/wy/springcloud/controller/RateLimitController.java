package com.wy.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wy.springcloud.entities.CommonResult;
import com.wy.springcloud.entities.Payment;
import com.wy.springcloud.myhandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    //**************************************以下为 A组 SentinelResource规则测试*****************************************

    @GetMapping("/byResource")
    //value代表在sentinel控制台（http://192.168.68.132:8080/）热点规则中配置的资源名（规则）
    //blockHandler代表触发规则限制时，转而调用的兜底方法
    //注意：若指定了热点规则而没有指定兜底方法的话，会将报错信息直接返给前端
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200, "按资源名称限流测试OK", new Payment(2020L, "serial001"));
    }

    public CommonResult handleException(BlockException exception) {
        return new CommonResult(444, exception.getClass().getCanonicalName() + "\t 服务不可用", null);
    }

    //**************************************以下为 B组 SentinelResource规则测试*****************************************

    @GetMapping("/rateLimit/byUrl")
    //当使用了SentinelResource，但没指定兜底方法时，系统默认会将报错发往前端
    @SentinelResource(
            value = "byUrl",
            //当触发熔断限流规则时，指定负责兜底的类
            blockHandlerClass = CustomerBlockHandler.class,
            //负责兜底类中的兜底方法
            blockHandler = "handlerException2"
    )
    public CommonResult byUrl() {
        return new CommonResult(200, "按url限流测试OK", new Payment(2020L, "serial002"));
    }

}
