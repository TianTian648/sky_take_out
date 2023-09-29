package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {
    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    Page<Dish> pageQuery(Integer categoryId, String name, Integer status);
}
