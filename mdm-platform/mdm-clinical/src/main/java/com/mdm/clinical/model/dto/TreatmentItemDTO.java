package com.mdm.clinical.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TreatmentItemDTO {

    @NotBlank(message = "项目名称不能为空")
    private String treatmentName;

    private Integer itemCategory;

    private Integer chargeCategory;

    private String insuranceCode;

    private String execDeptCode;

    private BigDecimal unitPrice;

    private String unit;

    private String orgCode;

    private Integer status;

    private String remark;

    private Integer version;

    private String approvalNo;
}
