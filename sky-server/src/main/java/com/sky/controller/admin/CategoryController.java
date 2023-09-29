package com.sky.controller.admin;

import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/category")
@RestController
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询显示相关
     *
     * @Param categoryPageQueryDTO
     */
    @GetMapping("/page")
    @ApiOperation("分页查询相关")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类的分页查询，参数为{}", categoryPageQueryDTO);
        return Result.success(categoryService.pageQuery(categoryPageQueryDTO));
    }

    /**
     * 启用or禁用开关
     *
     * @Param status
     * @Param id
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用or禁用开关")
    public Result startOrStop(@PathVariable("status") Integer status, Long id) {
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 新增分类
     *
     * @Param categoryDTO
     */
    @PostMapping
    @ApiOperation("新增分类")

    public Result save(@RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 根据ID删除分类
     *
     * @Param id
     */
    @DeleteMapping
    @ApiOperation("根据ID删除分类")
    public Result deleteById(Long id) {
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改菜品分类
     *
     * @Param categoryDTO
     */
    @PutMapping
    @ApiOperation("修改分类")
    public Result update(@RequestBody CategoryDTO categoryDTO) {
       categoryService.update(categoryDTO);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("分类查询")
    public Result<List<Category>> queryByType(Integer type) {
        return Result.success(categoryService.queryByType(type));

    }
}
