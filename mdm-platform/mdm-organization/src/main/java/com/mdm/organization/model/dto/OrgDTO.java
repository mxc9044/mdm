package com.mdm.organization.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrgDTO {
    @NotBlank(message = "机构名称不能为空")
    private String orgName;
    private String shortName;
    @NotNull(message = "机构类型不能为空")
    private Integer orgType;
    private String parentCode;
    private String regionCode;
    private String address;
    private String contactPhone;
    private String licenseNo;
    private Integer status;
    private String remark;
    private Integer version;
    private String approvalNo;
}
