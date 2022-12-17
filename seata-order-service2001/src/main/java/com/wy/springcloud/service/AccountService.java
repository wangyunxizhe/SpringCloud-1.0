package com.wy.springcloud.service;

import com.wy.springcloud.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * 模拟 账户服务 consumer端
 */
@FeignClient(value = "seata-account-service")
public interface AccountService {

    /**
     * 调用扣除余额的provider接口
     *
     * @param userId 用户id
     * @param money  要扣减的金额
     * @return
     */
    @PostMapping(value = "/account/decrease")
    CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);

}
