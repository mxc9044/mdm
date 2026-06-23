package com.mdm.clinical.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mdm.common.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mdm_treatment_item")
public class TreatmentItem extends BaseEntity {

    private String treatmentCode;

    private String treatmentName;

    private Integer itemCategory;

    private Integer chargeCategory;

    private String insuranceCode;

    private String execDeptCode;

    private BigDecimal unitPrice;

    private String unit;

    private String orgCode;

    private Integer status;

    @Version
    private Integer version;

    private String remark;
}
