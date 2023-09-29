package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    Page<Category> pageQuery(String name, Integer type);

    void startOrStop(Integer status, Long id);
    @AutoFill(value = OperationType.INSERT)
    void save(Category category);

    void deleteById(Long id);
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    List<Category> queryByType(Integer type);
}
