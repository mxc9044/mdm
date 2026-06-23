package com.mdm.supply.model.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DrugDTO {
    @NotBlank(message = "通用名不能为空") private String genericName;
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
    private String orgCode;
    private Integer status;
    private String remark;
    private Integer version;
    private String approvalNoOa;

    private Boolean enableSplit;
    private String packUnit;
    private Integer packQty;
    private String splitUnit;
    private BigDecimal splitPrice;
}
