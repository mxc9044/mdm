package com.mdm.organization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdm.organization.model.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
