package com.mdm.clinical.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TreatmentItemVO {

    private Long id;

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

    private Integer version;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
