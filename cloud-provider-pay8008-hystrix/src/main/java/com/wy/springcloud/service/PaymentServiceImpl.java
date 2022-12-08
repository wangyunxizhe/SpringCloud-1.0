package com.wy.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    /**
     * 正常返回的方法
     */
    @Override
    public String paymentInfoOK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " 触发 paymentInfoOK()，id:" + id + " *~*~";
    }

    /**
     * 模拟过一段时间后返回的方法
     */
    @Override
    @HystrixCommand(
            //使用HystrixCommand注释，指定失败兜底降级方法为paymentInfoTimeoutHandler
            fallbackMethod = "paymentInfoTimeoutHandler",
            //设置5秒为调用该方法的峰值，未达峰值正常返回，超过走降级方法
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")}
    )
    public String paymentInfoTimeout(Integer id) {
        //模拟该方法的运行时长
        int timeout = 3;

        //遇到运行报错时，同样也会转而触发fallback指定的方法
//        int i = 10 / 0;
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " 触发 paymentInfoTimeout()，id:" + id + " *~*~ 耗时（秒）：" + timeout;
    }

    //paymentInfoTimeout()的fallback兜底方法
    public String paymentInfoTimeoutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + " 8001系统繁忙或运行报错，请稍后重试，id:" + id + " *%*%";
    }

    /**
     * 服务熔断
     * 超时、异常 都会触发熔断
     * 1、默认是最近10秒内收到不小于10个请求（结合“请求次数”，“时间窗口期”两个配置项看）
     * 2、并且有60%是失败的（“失败率”配置项）
     * 3、满足以上条件，就开启断路器
     * 4、开启后所有请求将不再调用主逻辑，而是直接调用fallback的降级逻辑，起到熔断效果
     * 5、经过一段时间（时间窗口期，默认是5秒），断路器变为半开状态，会让其中一个请求进行转发
     * 5.1、如果成功，断路器会关闭，
     * 5.2、若失败，继续开启。重复4和5
     */
    @Override
    @HystrixCommand(
            fallbackMethod = "paymentCircuitBreakerFallback",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口期
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),// 失败率达到多少后跳闸
            }
    )
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("***** id不能为负数 *****");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    //paymentCircuitBreaker()的fallback兜底方法
    public String paymentCircuitBreakerFallback(Integer id) {
        return "id：" + id + "，id不能为负数，请重试~~~";
    }

}
