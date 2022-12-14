package com.wy.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() {
        // 测试阈值类型：线程数
        /*try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return "------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + "\t" + "...testB");
        return "------testB";
    }

    @GetMapping("/testC")
    public String testC() {
        log.info("testC 测试sentinel异常比例");
        int age = 10 / 0;
        return "------testC";
    }

    @GetMapping("/testD")
    public String testD() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testD 测试sentinel RT（平均响应时间）");
        return "------testD";
    }

    @GetMapping("/testE")
    public String testE() {
        log.info("testE 测试异常数");
        int age = 10 / 0;
        return "------testE 测试sentinel异常数";
    }

    /**
     * 测试sentinel热点key规则
     */
    @GetMapping("/testHotKey")
    //value代表在sentinel控制台（http://192.168.68.132:8080/）热点规则中配置的资源名（规则）
    //blockHandler代表触发规则限制时，转而调用的兜底方法
    //注意：若指定了热点规则而没有指定兜底方法的话，会将报错信息直接返给前端
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
//        int age = 10 / 0;
        return "------testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException exception) {
        //sentinel系统默认的提示：Blocked by Sentinel (flow limiting)
        return "------deal_testHotKey,o(╥﹏╥)o";
    }

}
