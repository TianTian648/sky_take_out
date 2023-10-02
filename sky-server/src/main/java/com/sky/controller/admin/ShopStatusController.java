package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopStatusController")
@RequestMapping("/admin/shop")
@Api(tags = "营业状态相关接口")
public class ShopStatusController {
    private final String key = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("设置营业状态")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable("status") Integer status) {

        redisTemplate.opsForValue().set(key, status);
        return Result.success();
    }
    @ApiOperation("查询店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        return Result.success((Integer) redisTemplate.opsForValue().get(key));
    }

}
