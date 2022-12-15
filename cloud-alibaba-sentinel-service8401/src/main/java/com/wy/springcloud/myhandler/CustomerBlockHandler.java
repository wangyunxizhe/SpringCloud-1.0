package com.wy.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wy.springcloud.entities.CommonResult;

/**
 * 该类负责处理sentinel熔断限流的全局返回兜底方法
 */
public class CustomerBlockHandler {

    public static CommonResult handlerException(BlockException exception) {
        return new CommonResult(4444, "按客戶自定义,global handlerException----1", null);
    }


    public static CommonResult handlerException2(BlockException exception) {
        return new CommonResult(4444, "按客戶自定义,global handlerException----2", null);
    }

}
