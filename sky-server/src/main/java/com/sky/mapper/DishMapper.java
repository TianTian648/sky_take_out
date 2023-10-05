package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {
    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    Page<DishVO> pageQuery(Integer categoryId, String name, Integer status);

    void deleteByIds(List<Long> ids);

    DishVO queryById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    List<Dish> showcategory(Dish dish);

    Integer countByMap(Map map);
}
