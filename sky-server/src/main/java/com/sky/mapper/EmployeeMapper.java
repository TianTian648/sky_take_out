package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */

    Employee getByUsername(String username);
    void save(Employee employee);
    Page<Employee> pageQuery(String name);

    void startOrStop(Integer status, Long id);

    void update(Employee employee);
}
