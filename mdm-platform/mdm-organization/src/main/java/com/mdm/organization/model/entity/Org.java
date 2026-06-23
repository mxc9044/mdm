package com.mdm.organization.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mdm_org")
public class Org extends BaseEntity {
    private String orgCode;
    private String orgName;
    private String shortName;
    private Integer orgType;
    private String parentCode;
    private String regionCode;
    private String address;
    private String contactPhone;
    private String licenseNo;
    private Integer status;
    @Version
    private Integer version;
    private String remark;
}
