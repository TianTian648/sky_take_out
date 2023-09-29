package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "菜品管理")
@RestController
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @ApiOperation("新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
           dishService.saveWithFlavor(dishDTO);
           return Result.success();
    }

    /**
     * 菜品分页查询展示
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("page")
    @ApiOperation("菜品分页查询展示")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
         return Result.success(dishService.pageQuery(dishPageQueryDTO));
    }
    @GetMapping("/list")
    @ApiOperation("菜品分类的展示")
    public Result showcategory() {
      return Result.success();
    }
}
