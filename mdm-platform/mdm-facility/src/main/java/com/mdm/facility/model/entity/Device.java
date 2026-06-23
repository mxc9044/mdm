package com.mdm.facility.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mdm_device")
public class Device extends BaseEntity {
    private String deviceCode;
    private String deviceName;
    private Integer deviceCategory;
    private String brand;
    private String specification;
    private String orgCode;
    private String deptCode;
    private String assetNo;
    private String regCertNo;
    private LocalDate purchaseDate;
    private Integer serviceLife;
    private LocalDate warrantyExpire;
    private Integer status;
    @Version
    private Integer version;
    private String remark;
}
