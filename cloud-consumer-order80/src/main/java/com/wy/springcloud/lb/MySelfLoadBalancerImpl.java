package com.wy.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现自定义负载算法的接口实现类
 */
@Component
public class MySelfLoadBalancerImpl implements MySelfLoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /*
    通过当前数字自增1
     */
    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            //Integer最大就是2147483647，所以到达阈值时，需要重新开始从0计数
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!this.atomicInteger.compareAndSet(current, next));
        System.err.println("~~~current:" + current);
        System.err.println("~~~next:" + next);
        return next;
    }

    /**
     * 模仿LoadBalancer源码实现自定义的轮询算法
     *
     * @param serviceInstances
     * @return
     */
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        //获取一个数字作为list中的下标
        int index = getAndIncrement() % serviceInstances.size();
        //通过下标返回服务provider对象
        return serviceInstances.get(index);
    }

}
