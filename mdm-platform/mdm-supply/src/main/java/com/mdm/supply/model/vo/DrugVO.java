package com.mdm.supply.model.vo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DrugVO {
    private Long id;
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
    private Integer version;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private List<DrugVO> children;
}
