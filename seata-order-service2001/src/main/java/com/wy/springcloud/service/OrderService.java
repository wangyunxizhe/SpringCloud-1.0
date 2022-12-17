package com.wy.springcloud.service;

import com.wy.springcloud.domain.Order;

public interface OrderService {

    /**
     * 模拟创建订单
     *
     * @param order 订单
     */
    void create(Order order);

}
