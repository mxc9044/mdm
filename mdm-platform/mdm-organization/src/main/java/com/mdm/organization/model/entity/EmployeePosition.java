package com.mdm.organization.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mdm_employee_position")
public class EmployeePosition extends BaseEntity {
    private String empCode;
    private String orgCode;
    private String deptCode;
    private Integer positionType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
}
