package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    List<Long> queryByDishIds(List<Long> ids);

    void save(List<SetmealDish> setmealDishes);

    void deleteByIds(List<Long> ids);

    List<SetmealDish> queryById(Long id);



}
