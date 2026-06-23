package com.mdm.supply.model.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplyItemDTO {
    @NotBlank(message = "物资名称不能为空") private String supplyItemName;
    private Integer itemCategory;
    private String specification;
    private String unit;
    private String orgCode;
    private String brand;
    private Integer storageCondition;
    private Integer status;
    private String remark;
    private Integer version;
    private String approvalNo;
}
