package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车管理")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation("添加购物车")
    @PostMapping("/add")
    public Result save(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.save(shoppingCartDTO);
        return Result.success();
    }

    @ApiOperation("查看购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> viewShoppingCart() {
        return Result.success(shoppingCartService.viewShoppingCart());
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result cleanAll() {
        shoppingCartService.cleanAll();
        return Result.success();
    }

    /**
     * 删除购物车中的一个商品
     *
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("sub")
    @ApiOperation("删除购物车中的一个商品")
    public Result deleteOne(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.deleteOne(shoppingCartDTO);
        return Result.success();
    }
}
