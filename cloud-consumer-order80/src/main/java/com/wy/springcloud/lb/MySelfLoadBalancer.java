package com.wy.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 实现自定义负载算法的接口
 */
public interface MySelfLoadBalancer {

    /**
     * 模仿LoadBalancer源码实现自定义的轮询算法
     *
     * @param serviceInstances
     * @return
     */
    ServiceInstance instances(List<ServiceInstance> serviceInstances);

}
