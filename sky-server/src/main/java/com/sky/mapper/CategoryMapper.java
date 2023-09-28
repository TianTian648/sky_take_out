package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    Page<Category> pageQuery(String name, Integer type);

    void startOrStop(Integer status, Long id);

    void save(Category category);

    void deleteById(Long id);

    void update(Category category);
}
