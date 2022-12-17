package com.wy.springcloud.service;

import com.wy.springcloud.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 模拟 库存服务 consumer端
 */
@FeignClient(value = "seata-storage-service")
public interface StorageService {

    /**
     * 调用减库存的provider接口
     *
     * @param productId 产品ID
     * @param count     要减少的产品数量
     * @return
     */
    @PostMapping(value = "/storage/decrease")
    CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);

}
