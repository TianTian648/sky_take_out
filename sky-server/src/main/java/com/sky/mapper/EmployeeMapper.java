package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {


    Employee getByUsername(String username);
    @AutoFill(value = OperationType.INSERT)
    void save(Employee employee);
    Page<Employee> pageQuery(String name);

    void startOrStop(Integer status, Long id);
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    Employee queryById(Long id);
}
