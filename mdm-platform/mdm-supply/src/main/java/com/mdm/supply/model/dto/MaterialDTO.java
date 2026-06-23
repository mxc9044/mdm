package com.mdm.supply.model.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaterialDTO {
    @NotBlank(message = "耗材名称不能为空") private String materialName;
    private Integer materialCategory;
    private String specification;
    private String unit;
    private String manufacturer;
    private String regCertNo;
    private String insuranceCode;
    private String orgCode;
    private Integer status;
    private String remark;
    private Integer version;
    private String approvalNo;
}
