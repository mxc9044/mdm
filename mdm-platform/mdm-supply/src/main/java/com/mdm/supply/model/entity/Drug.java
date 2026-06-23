package com.mdm.supply.model.entity;
import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data @EqualsAndHashCode(callSuper = true) @TableName("mdm_drug")
public class Drug extends BaseEntity {
    private String drugCode;
    private String parentDrugCode;
    private Integer isSplit;
    private String genericName;
    private String tradeName;
    private String dosageForm;
    private String specification;
    private String unit;
    private BigDecimal unitPrice;
    private String manufacturer;
    private String approvalNo;
    private String insuranceCode;
    private Integer drugCategory;
    private Integer storageCondition;
    private String packUnit;
    private Integer packQty;
    private String splitUnit;
    private BigDecimal splitPrice;
    private String orgCode;
    private Integer status;
    @Version private Integer version;
    private String remark;
}
