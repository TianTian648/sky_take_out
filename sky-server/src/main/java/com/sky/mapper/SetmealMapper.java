package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @AutoFill(value = OperationType.INSERT)
    void save(Setmeal setmeal);

    Page<SetmealVO> pageQUery(Setmeal setmeal);

    void deleteByIds(List<Long> ids);

    SetmealVO queryById(Long id);
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);
}
