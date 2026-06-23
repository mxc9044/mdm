package com.mdm.organization.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mdm_dept")
public class Dept extends BaseEntity {
    private String deptCode;
    private String deptName;
    private String aliasName;
    private Integer deptType;
    private String orgCode;
    private String parentCode;
    private Integer sortOrder;
    private Integer status;
    @Version
    private Integer version;
    private String remark;
}
