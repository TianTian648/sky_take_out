package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {


    void save(List<DishFlavor> flavors);

    void deleteByDishIds(List<Long> ids);

    List<DishFlavor> queryById(Long dishId);


}
