package com.mdm.organization.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mdm_employee")
public class Employee extends BaseEntity {
    private String empCode;
    private String empName;
    private String idCard;
    private String phone;
    private Integer gender;
    private String practiceCert;
    private String titleCert;
    private String orgCode;
    private String deptCode;
    private String position;
    private String title;
    private Integer practiceType;
    private Integer status;
    @Version
    private Integer version;
    private String remark;
}
